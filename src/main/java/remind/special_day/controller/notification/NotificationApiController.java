package remind.special_day.controller.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.dto.firebase.NotificationRequest;
import remind.special_day.service.MemberService;
import remind.special_day.service.NotificationService;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NotificationApiController {

    private final NotificationService notificationService;
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody String token) {
        try {
            notificationService.register(1L, token);
            log.info("알람 설정 완료 token : " + token);
            return ResponseEntity.ok().build();
        } catch(RuntimeException e) {
            log.error("로그인 되어있지 않음..");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/notification/sample")
    public String sample() {
        return "fragments/sample";
    }

    private void createReceiveNotification(ChatLogRequestDto chatLog) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("CHAT RECEIVED")
                .token(notificationService.getToken(chatLog.getReceive_chatId()))
                .message(chatLog.getMessage())
                .build();
        notificationService.sendNotification(notificationRequest);
    }

}
