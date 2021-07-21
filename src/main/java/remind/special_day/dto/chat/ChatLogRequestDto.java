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
    private String sender;
    private String receiver;
    private Long chatId;
}
