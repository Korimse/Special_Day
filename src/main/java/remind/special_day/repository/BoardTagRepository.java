package remind.special_day.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import remind.special_day.domain.BoardTag;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
}
