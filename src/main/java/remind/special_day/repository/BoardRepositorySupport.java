package remind.special_day.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import remind.special_day.domain.*;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositorySupport {

    private final QBoard board = QBoard.board;
    private final QBoardTag boardTag = QBoardTag.boardTag;
    private final QTag tag1 = QTag.tag1;
    private final QMember member = QMember.member;
    private final JPAQueryFactory jpaQueryFactory;

    public List<Board> findByTag(String tag) {
        return jpaQueryFactory.selectFrom(board)
                .join(board.member, member)
                .fetchJoin()
                .join(board.boardTags, boardTag)
                .fetchJoin()
                .join(boardTag.tag, tag1)
                .fetchJoin()
                .where(tag1.tag.in(tag))
                .fetch();
    }

    public List<Board> findByMember(String email) {
        return jpaQueryFactory.selectFrom(board)
                .distinct()
                .join(board.member, member)
                .fetchJoin()
                .join(board.boardTags, boardTag)
                .fetchJoin()
                .join(boardTag.tag, tag1)
                .fetchJoin()
                .where(board.member.email.eq(email))
                .fetch();
    }
}
