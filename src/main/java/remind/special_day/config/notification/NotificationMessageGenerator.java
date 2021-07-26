package remind.special_day.config.notification;

import remind.special_day.domain.Member;
import remind.special_day.dto.chat.ChatLogRequestDto;

@FunctionalInterface
public interface NotificationMessageGenerator {

    String generateNotificationMessage(ChatLogRequestDto receiver);
}
