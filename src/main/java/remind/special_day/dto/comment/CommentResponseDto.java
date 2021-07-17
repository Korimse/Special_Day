package remind.special_day.dto.comment;

import lombok.Builder;
import lombok.Getter;
import remind.special_day.domain.Comment;

@Getter
@Builder
public class CommentResponseDto {

    private Long board_id;
    private String comment;
    private String email;

    public static CommentResponseDto dto(Comment comment) {
        return CommentResponseDto.builder()
                .board_id(comment.getBoard().getId())
                .comment(comment.getComment())
                .email(comment.getMember().getEmail())
                .build();
    }
}
