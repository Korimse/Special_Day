package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.repository.ChatLogRepository;
import remind.special_day.repository.ChatRepository;
import remind.special_day.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatLogRepository chatLogRepository;
    private final MemberRepository memberRepository;

    
}
