package remind.special_day.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_log_id")
    private Long id;

    private String message;
    private String sender;
    private String receiver;
    private LocalDateTime createDate;

    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Builder
    public ChatLog(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void updateDate() {
        this.createDate = LocalDateTime.now();
    }

    public void updateChecked() {
        this.checked = true;
    }

    public void addChat(Chat chat) {
        this.chat = chat;
        this.chat.getChatLogs().add(this);
    }
}
