package remind.special_day.controller.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.config.notification.NotificationType;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.dto.firebase.NotificationRequest;
import remind.special_day.service.MemberService;
import remind.special_day.service.NotificationService;
import remind.special_day.util.SecurityUtil;

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

    @PostMapping("/notification/sample")
    public String post_notice(@RequestParam("email") String email) {
        ChatLogRequestDto chat = ChatLogRequestDto.builder()
                .receive_chatId(1L)
                .message("Login 하였습니다.")
                .createDate(LocalDateTime.now())
                .build();
        createReceiveNotification(chat);
        log.info("chat : " + chat.getMessage());
        return "fragments/sample";
    }

    private void createReceiveNotification(ChatLogRequestDto chatLog) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .title("CHAT RECEIVED")
                .token(notificationService.getToken(chatLog.getReceive_chatId()))
                .message(NotificationType.POST_RECEIVED.generateNotificationMessage(chatLog))
                .build();
        notificationService.sendNotification(notificationRequest);
    }

}
