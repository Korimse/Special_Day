package remind.special_day.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Chat chat;

    @Builder
    public ChatLog(String message, String sender, String receiver) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void addChat(Chat chat) {
        this.chat = chat;
        this.chat.getChatLogs().add(this);
    }
}
