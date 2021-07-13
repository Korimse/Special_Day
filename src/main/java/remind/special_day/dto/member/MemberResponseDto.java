package remind.special_day.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import remind.special_day.domain.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail());
    }
}
