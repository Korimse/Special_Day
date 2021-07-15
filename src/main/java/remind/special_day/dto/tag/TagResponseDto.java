package remind.special_day.dto.tag;

import lombok.*;
import remind.special_day.domain.Tag;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponseDto {

    private Long id;
    private String tag;

    public TagResponseDto(Tag tag) {
        this.tag = tag.getTag();
    }
}
