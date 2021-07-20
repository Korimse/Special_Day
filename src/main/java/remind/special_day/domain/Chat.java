package remind.special_day.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Chat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    private String sender;

    private String receiver;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatLog> chatLogs = new ArrayList<>();

    @Builder
    public Chat(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
