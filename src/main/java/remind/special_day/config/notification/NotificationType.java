package remind.special_day.config.notification;

import remind.special_day.domain.Member;
import remind.special_day.dto.chat.ChatLogRequestDto;

public enum NotificationType {

    POST_RECEIVED (((receiver) ->
            receiver.getReceive_chatId() + "에게 글을 작성하였습니다."));

    private NotificationMessageGenerator notificationMessageGenerator;

    NotificationType(NotificationMessageGenerator notificationMessageGenerator) {
        this.notificationMessageGenerator = notificationMessageGenerator;
    }

    public String generateNotificationMessage(ChatLogRequestDto receiver) {
        return notificationMessageGenerator.generateNotificationMessage(receiver);
    }
}
