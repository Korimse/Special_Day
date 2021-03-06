package remind.special_day.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import remind.special_day.domain.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Override
    @EntityGraph(attributePaths = {"member", "boardTags", "boardTags.tag"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Board> findAll();

}
