package remind.special_day.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatLogRequestDto {

    private String message;
    private String email;
    private Long receive_chatId;
    private LocalDateTime createDate;

    public void updateCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public ChatLogRequestDto(String message, String email, Long receive_chatId, LocalDateTime createDate) {
        this.email = email;
        this.message = message;
        this.receive_chatId = receive_chatId;
        this.createDate = createDate;
    }
}
