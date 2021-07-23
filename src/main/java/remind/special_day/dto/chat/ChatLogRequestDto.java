package remind.special_day.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatLogRequestDto {

    private String message;
    private Long receive_chatId;
    private LocalDateTime createDate;

    public void updateCreateDate() {
        this.createDate = LocalDateTime.now();
    }
}
