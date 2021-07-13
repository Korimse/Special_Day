package remind.special_day.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Chat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String sender;

    private String receiver;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;


}
