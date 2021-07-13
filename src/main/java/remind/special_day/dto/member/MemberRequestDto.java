package remind.special_day.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import remind.special_day.domain.Member;
import remind.special_day.domain.MemberRole;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    private String email;
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return new Member(email, passwordEncoder.encode(password), MemberRole.ROLE_USER);
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
