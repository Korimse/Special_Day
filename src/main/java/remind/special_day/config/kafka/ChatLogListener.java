package remind.special_day.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatLogListener {

    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = "foo")
    public void listen(ChatLogRequestDto chatLog) {
//        log.info("sending via kafka listener.. kafka-chat");
        log.info("chatLog = " + chatLog.getMessage() + "  receive = " + chatLog.getReceive_chatId());
        chatLog.updateCreateDate();
        template.convertAndSend("/topic/group/" + chatLog.getReceive_chatId(), chatLog);
    }
}
