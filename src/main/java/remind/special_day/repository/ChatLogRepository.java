package remind.special_day.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import remind.special_day.domain.Chat;
import remind.special_day.domain.ChatLog;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

    @EntityGraph(attributePaths = {"chat"})
    List<ChatLog> findAllByChatIdAndCheckedAndReceiver(Long id, boolean isCheck, String email);

    int countAllByReceiverAndChecked(String email, boolean isCheck);
}
