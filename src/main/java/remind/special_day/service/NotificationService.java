package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import remind.special_day.config.firebase.FCMService;
import remind.special_day.dto.firebase.NotificationRequest;

import javax.management.Notification;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FCMService fcmService;

    private final Map<Long, String> tokenMap = new HashMap<>();

    public void register(final Long userId, final String token) {
        tokenMap.put(userId, token);
    }

    public void deleteToken(final Long userId) {
        tokenMap.remove(userId);
    }

    public String getToken(final Long userId) {
        return tokenMap.get(userId);
    }

    public void sendNotification(final NotificationRequest request) {
        try{
            fcmService.send(request);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
    }
}
