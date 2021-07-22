package remind.special_day.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import remind.special_day.domain.ChatLog;
import remind.special_day.service.ChatService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatLogListener {

    private final SimpMessagingTemplate template;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = "foo")
    public void listen(ChatLog chatLog) {
        log.info("sending via kafka listener.. kafka-chat");
        Long id = chatLog.getChat().getId();
        template.convertAndSend("/topic/group/" + id, chatLog);
    }
}
