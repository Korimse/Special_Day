package remind.special_day.dto.comment;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CommentUpdateRequestDto {

    private Long id;

    private Long boardId;

    @NotEmpty
    private String comment;


}
