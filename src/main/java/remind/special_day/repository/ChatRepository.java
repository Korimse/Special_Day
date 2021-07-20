package remind.special_day.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remind.special_day.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
