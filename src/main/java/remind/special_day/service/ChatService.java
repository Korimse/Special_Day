package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.domain.Chat;
import remind.special_day.domain.ChatLog;
import remind.special_day.dto.chat.ChatListResponseDto;
import remind.special_day.dto.chat.ChatLogRequestDto;
import remind.special_day.dto.chat.ChatRequestDto;
import remind.special_day.repository.ChatLogRepository;
import remind.special_day.repository.ChatRepository;
import remind.special_day.repository.MemberRepository;
import remind.special_day.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatLogRepository chatLogRepository;
    private final MemberRepository memberRepository;

    /**
     * Chat 추가
     */
    @Transactional
    public Long addChat(ChatRequestDto chatRequestDto) {
        Chat chat = Chat.builder()
                .sender(chatRequestDto.getSender())
                .receiver(chatRequestDto.getReceiver())
                .build();
        chatRepository.save(chat);
        return chat.getId();
    }

    /**
     * ChatLog 추가
     */
    @Transactional
    public Long addChatLog(ChatLogRequestDto chatLogRequestDto) {
        Long chatId = chatLogRequestDto.getChatId();
        Chat chat = chatRepository.findById(chatId).orElseThrow(RuntimeException::new);

        ChatLog chatLog = ChatLog.builder()
                .message(chatLogRequestDto.getMessage())
                .sender(chatLogRequestDto.getSender())
                .receiver(chatLogRequestDto.getReceiver())
                .createDate(LocalDateTime.now())
                .build();

        chatLog.addChat(chat);

        chatLogRepository.save(chatLog);
        return chatLog.getId();
    }

    /**
     * Chat 조회 by email
     */
    public List<ChatListResponseDto> findChatByEmail(String email) {
        List<ChatListResponseDto> chatList = chatRepository.findBySender(email).stream()
                .map(ChatListResponseDto::dto)
                .collect(Collectors.toList());

        return chatList;
    }


    /**
     * ChatLog 조회 by ChatId
     */
    public List<ChatLog> findChatLogByChatId(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(RuntimeException::new);

        return chat.getChatLogs();
    }

    /**
     * ChatLog 조회 by ChatId, !checked
     */
    public int countChatLogByChatId(Long chatId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Chat chat = chatRepository.findById(chatId).orElseThrow(RuntimeException::new);
        List<ChatLog> chatLogs = chat.getChatLogs();
        int count = 0;

        for (ChatLog chatLog : chatLogs) {
            if(!chatLog.isChecked() && currentMemberId.equals(chatLog.getReceiver())) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * ChatLog count by !checked
     */
    public int countChatLogByNotChecked(String email) {
        return chatLogRepository.countAllByReceiverAndChecked(email, false);
    }

    /**
     * Update !checked -> checked
     */
    @Transactional
    public void updateChatLogRead(Long chatId, String email) {
        List<ChatLog> allByChatIdAndCheckedAndReceiver = chatLogRepository.findAllByChatIdAndCheckedAndReceiver(chatId, false, email);
        allByChatIdAndCheckedAndReceiver.forEach(ChatLog::updateChecked);
    }

}
