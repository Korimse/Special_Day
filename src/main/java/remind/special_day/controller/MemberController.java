package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import remind.special_day.dto.jwt.TokenDto;
import remind.special_day.dto.jwt.TokenRequestDto;
import remind.special_day.dto.member.MemberRequestDto;
import remind.special_day.dto.member.MemberResponseDto;
import remind.special_day.service.MemberService;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok(memberService.logout());
    }
}
