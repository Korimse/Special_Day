package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.config.kafka.KafkaConstants;
import remind.special_day.domain.Chat;
import remind.special_day.domain.ChatLog;
import remind.special_day.dto.chat.ChatListResponseDto;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.dto.chat.ChatRequestDto;
import remind.special_day.service.ChatService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final KafkaTemplate<String, ChatLogRequestDto> kafkaTemplate;

    @PostMapping("/kafka/publish")
    public ResponseEntity<Void> sendMessages(@RequestBody ChatLogRequestDto chatLog) {
        try{
            log.info("Produce message : " + chatLog.toString());
            log.info(chatLog.getMessage());

            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, chatLog).get();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/chat")
    public ResponseEntity<Long> addChatRoom(@RequestBody ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(chatService.addChat(chatRequestDto));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<ChatLog>> getChatLogByChatId(@PathVariable("chatId") Long chatId) {
        return ResponseEntity.ok(chatService.findChatLogByChatId(chatId));
    }

    @GetMapping("/chat")
    public ResponseEntity<List<ChatListResponseDto>> getChats(@RequestParam String email) {
        return ResponseEntity.ok(chatService.findChatByEmail(email));
    }

    @GetMapping("/chat/read")
    public ResponseEntity<Integer> countChatLogNotChecked(@RequestParam String email) {
        return ResponseEntity.ok(chatService.countChatLogByNotChecked(email));
    }

    @GetMapping("/chat/readcount")
    public ResponseEntity<Integer> countChatLogNotCheckedById(@RequestParam Long id) {
        return ResponseEntity.ok(chatService.countChatLogByChatId(id));
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Long> updateChecked(@PathVariable Long chatId, @RequestParam String email) {
        return ResponseEntity.ok(chatService.updateChatLogRead(chatId, email));
    }
}
