package remind.special_day.dto.chat;

import lombok.Builder;
import lombok.Getter;
import remind.special_day.domain.Chat;

@Getter
@Builder
public class ChatListResponseDto {

    private String sender;
    private String receiver;
    private String lastMessage;

    public static ChatListResponseDto dto(Chat chat) {
        return ChatListResponseDto.builder()
                .sender(chat.getSender())
                .receiver(chat.getReceiver())
                .lastMessage(chat.getChatLogs().get(chat.getChatLogs().size()-1).getMessage())
                .build();
    }
}
