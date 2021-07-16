package remind.special_day.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BoardTag {

    @Id
    @GeneratedValue
    @Column(name = "board_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void addBoard(Board board) {
        this.board = board;
        this.board.getBoardTags().add(this);
    }

    public void addTag(Tag tag) {
        this.tag = tag;
    }


}
