package remind.special_day.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String comment;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void addMember(Member member) {
        this.member = member;
        this.member.getComments().add(this);
    }

    public void addBoard(Board board) {
        this.board = board;
        this.board.getComments().add(this);
    }

    public void changeComment(String comment) {
        this.comment = comment;
    }

}
