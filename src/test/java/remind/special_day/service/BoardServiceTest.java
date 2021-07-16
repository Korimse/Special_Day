package remind.special_day.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import remind.special_day.domain.Board;
import remind.special_day.domain.QBoard;
import remind.special_day.domain.QBoardTag;
import remind.special_day.domain.QTag;
import remind.special_day.dto.tag.TagResponseDto;
import remind.special_day.repository.BoardRepositorySupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    BoardRepositorySupport boardRepositorySupport;

    @Test
    void findByTagTest() {
        QBoard board = QBoard.board;
        QBoardTag boardTag = QBoardTag.boardTag;
        QTag tag1 = QTag.tag1;

//        List<Board> boardList = boardRepositorySupport.findByMember("anrimy");
//        Set<String> collect1 = boardList.stream().map(board1 -> board1.getContent()).collect(Collectors.toSet());
//        System.out.println("fetch = " + collect1);

    }
}