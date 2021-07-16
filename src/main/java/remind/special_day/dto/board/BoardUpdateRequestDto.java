package remind.special_day.dto.board;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class BoardUpdateRequestDto {

    private String content;
    private Set<String> tags;
}
