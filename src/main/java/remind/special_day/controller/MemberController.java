package remind.special_day.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.dto.jwt.TokenDto;
import remind.special_day.dto.jwt.TokenRequestDto;
import remind.special_day.dto.member.MemberRequestDto;
import remind.special_day.dto.member.MemberResponseDto;
import remind.special_day.service.MemberService;

@Api(tags = {"1. Member"})
@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiImplicitParams({
            @ApiImplicitParam(name="email", value="이메일", required = true),
            @ApiImplicitParam(name="password", value = "비밀번호", required = true)
    })
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.signup(memberRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="email", value="이메일", required = true),
            @ApiImplicitParam(name="password", value = "비밀번호", required = true)
    })
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
