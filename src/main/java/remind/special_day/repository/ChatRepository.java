package remind.special_day.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remind.special_day.domain.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySender(String sender);
}
