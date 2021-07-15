package remind.special_day.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String tag;

    public Tag(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }
}
