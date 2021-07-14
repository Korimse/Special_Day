package remind.special_day.service;

import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import remind.special_day.config.jwt.TokenProvider;
import remind.special_day.domain.Member;
import remind.special_day.domain.MemberRole;
import remind.special_day.domain.RefreshToken;
import remind.special_day.dto.jwt.TokenDto;
import remind.special_day.dto.jwt.TokenRequestDto;
import remind.special_day.dto.member.MemberRequestDto;
import remind.special_day.dto.member.MemberResponseDto;
import remind.special_day.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    RedisService redisService;

    @AfterEach
    void cleanup() {
        memberRepository.deleteAll();
    }

    @Test
    void 회원가입() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("member1", "1234");

        Member member1 = memberRequestDto.toMember(passwordEncoder);

        MemberResponseDto of = MemberResponseDto.of(memberRepository.save(member1));

        Optional<Member> getMember = memberRepository.findByEmail(of.getEmail());

        assertThat(member1.getEmail()).isEqualTo(getMember.get().getEmail());
    }

    @Test
    void 로그인() {
        //signup
        MemberRequestDto memberRequestDto = new MemberRequestDto("member1", "1234");
        Member member1 = memberRequestDto.toMember(passwordEncoder);
        MemberResponseDto.of(memberRepository.save(member1));

        //create authenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();

        //validation
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //create jwt
        TokenDto tokenDto = tokenProvider.createToken(authentication);

        //save refreshToken
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        //save refreshToken in redis
        redisService.setData(memberRequestDto.getEmail(), refreshToken.getValue());
        redisService.setDataExpire(memberRequestDto.getEmail(), refreshToken.getValue(), 36000);

        assertThat(refreshToken.getValue()).isEqualTo(redisService.getData(memberRequestDto.getEmail()));
    }

    @Test
    void 재발급() {

        TokenRequestDto tokenRequestDto = new TokenRequestDto("mujeup01", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTYyNjI3NTcxNX0.XafGwlkHuXCJ8CFwiSMOyeXaBkSGIdwCDsAbXVC72KqkSfegyWhD5WjWWFrp9uns1xIr6EWiUYLBaOAI9N1VQA", "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2MjY1NzYzNDJ9.IG-3xqYnIkLDoM9gE-dCpgTvU-UeOchSqk5z-O7L3LAh_FoM6j3-cunFc0eCU7D4mSq_K0tpTz-OZzMDtYw5hg");

        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        TokenDto tokenDto = tokenProvider.createToken(authentication);

        if (!tokenProvider.validateToken(tokenDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

    }

}