package remind.special_day.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import remind.special_day.domain.ChatLog;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    @EntityGraph(attributePaths = {"chat"})
    List<ChatLog> findAllByChatIdAndCheckedAndReceiver(Long id, boolean isCheck, String email);


}
