package remind.special_day.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import remind.special_day.domain.Board;
import remind.special_day.domain.Comment;
import remind.special_day.domain.Member;
import remind.special_day.dto.comment.CommentAddRequestDto;
import remind.special_day.repository.BoardRepository;
import remind.special_day.repository.CommentRepository;
import remind.special_day.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;


    @Test
    Long 아왜안돼() {
        CommentAddRequestDto commentAddRequestDto = new CommentAddRequestDto(3L, "hihihi");
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        Board board = boardRepository.findById(commentAddRequestDto.getBoardId()).orElseThrow(RuntimeException::new);
        System.out.println("board = " + board);

//        Comment comment = commentAddRequestDto.dto(commentAddRequestDto);
//        comment.addMember(member);
//        comment.addBoard(board);
//        commentRepository.save(comment);

        return 12L;
    }

}