package remind.special_day.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import remind.special_day.domain.Board;
import remind.special_day.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"member", "board"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> findAllByBoardId(Long id);

    @EntityGraph(attributePaths = {"member", "board"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> findAllByMemberId(Long id);
}
