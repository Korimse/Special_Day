package remind.special_day.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String tag;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private Set<BoardTag> boardTags = new HashSet<>();
}
