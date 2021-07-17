package remind.special_day.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import remind.special_day.domain.Comment;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddRequestDto {

    private Long boardId;

    @NotEmpty
    private String comment;

    public static Comment dto(CommentAddRequestDto requestDto) {
        return Comment.builder()
                .comment(requestDto.comment)
                .createDate(LocalDateTime.now())
                .build();
    }
}
