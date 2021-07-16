package remind.special_day.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "album_id")
    private Long id;
    private String url;
    private String filename;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Album(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    public void addBoard(Board board) {
        this.board = board;
        this.board.getAlbums().add(this);
    }
}
