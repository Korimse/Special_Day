package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.domain.ChatLog;
import remind.special_day.dto.chat.ChatListResponseDto;
import remind.special_day.dto.chat.ChatRequestDto;
import remind.special_day.service.ChatService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

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
