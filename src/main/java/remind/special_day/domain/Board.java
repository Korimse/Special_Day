package remind.special_day.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.naming.ldap.HasControls;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String area;
    private String content;
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private Set<BoardTag> boardTags = new HashSet<>();

    @Builder
    public Board(String area, String content, LocalDateTime createDate) {
        this.area = area;
        this.content = content;
        this.createDate = createDate;
    }
}
