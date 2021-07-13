package remind.special_day.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@RequiredArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    @Column(name = "album_id")
    private Long id;

    private String filename;
}
