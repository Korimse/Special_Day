package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.config.jwt.TokenProvider;
import remind.special_day.domain.Member;
import remind.special_day.domain.RefreshToken;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.dto.firebase.NotificationRequest;
import remind.special_day.dto.jwt.TokenDto;
import remind.special_day.dto.jwt.TokenRequestDto;
import remind.special_day.dto.member.MemberRequestDto;
import remind.special_day.dto.member.MemberResponseDto;
import remind.special_day.repository.MemberRepository;
import remind.special_day.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final NotificationService notificationService;


    /**
     * 회원가입
     */
    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        log.info("member save");
        return MemberResponseDto.of(memberRepository.save(member));
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

//        refreshTokenRepository.save(refreshToken);

        // Redis Save
        redisService.setData(authentication.getName(), refreshToken.getValue());
        redisService.setDataExpire(authentication.getName(), refreshToken.getValue(), 36000);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto login_withNotification(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

//        refreshTokenRepository.save(refreshToken);

        // Redis Save
        redisService.setData(authentication.getName(), refreshToken.getValue());
        redisService.setDataExpire(authentication.getName(), refreshToken.getValue(), 36000);

        ChatLogRequestDto chat = ChatLogRequestDto.builder()
                .receive_chatId(1L)
                .message(memberRequestDto.getEmail() + "님이 Login 하였습니다.")
                .createDate(LocalDateTime.now())
                .build();

        createReceiveNotification(chat);

        // 5. 토큰 발급
        return tokenDto;
    }

    /**
     * 재발급
     */
    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        String refreshToken = redisService.getData(authentication.getName());


//        Optional<Member> byId1 = memberRepository.findById(Long.parseLong(authentication.getName()));

        if (!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 4. Refresh Token 일치하는지 검사
//        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
//            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        // 6. 저장소 정보 업데이트
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);
        redisService.setData(authentication.getName(), tokenDto.getRefreshToken());
        redisService.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), 36000);

        // 토큰 발급
        return tokenDto;
    }

    /**
     * 로그아웃
     */
    @Transactional
    public String logout() {
        MemberResponseDto myInfo = getMyInfo();
        try{
            redisService.deleteData(SecurityUtil.getCurrentMemberId().toString());
        } catch (RuntimeException e) {
            log.info("이미 로그아웃 처리가 되었씁니다.");
        }
        log.info(myInfo.getEmail()+"logout 되었습니다.");
        return myInfo.getEmail();
    }

    /**
     * Member 조회 by email
     */
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    /**
     * 현재 로그인된 Member 조회
     */
    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /**
     * Member 유효성 검사
     */
    public boolean isValidateDuplicateMember(Member member) {
        Optional<Member> getMember = memberRepository.findByEmail(member.getEmail());
        return getMember.isEmpty();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getMemberRole().toString());
        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    private void createReceiveNotification(ChatLogRequestDto chatLog) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("LOGIN MESSAGE")
                .token(notificationService.getToken(chatLog.getReceive_chatId()))
                .message(chatLog.getMessage())
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}
