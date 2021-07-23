package remind.special_day;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import remind.special_day.domain.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member1 = createMember("mujeup", "1234");
            Member member2 = createMember("anrimy", "12345");
            em.persist(member1);
            em.persist(member2);

            Board board1 = createBoard("Incheon", "my home", member1);
            Board board2 = createBoard("Seoul", "trip", member2);
            em.persist(board1);
            em.persist(board2);

            Comment comment1 = createComment(board1, member1, "hi");
            Comment comment2 = createComment(board1, member2, "Oh, Me too");
            Comment comment3 = createComment(board1, member2, "hello");
            Comment comment4 = createComment(board2, member1, "Piano man");
            Comment comment5 = createComment(board2, member2, "Singing");
            em.persist(comment1);
            em.persist(comment2);
            em.persist(comment3);
            em.persist(comment4);
            em.persist(comment5);


            Tag tag1 = createTag("home");
            Tag tag2 = createTag("travel");
            em.persist(tag1);
            em.persist(tag2);

            BoardTag boardTag = createBoardTag(board1, tag1);
            BoardTag boardTag1 = createBoardTag(board1, tag2);
            BoardTag boardTag2 = createBoardTag(board2, tag2);
            em.persist(boardTag1);
            em.persist(boardTag);
            em.persist(boardTag2);

        }

        private Member createMember(String email, String password){
            Member member = new Member();
            member.setEmail(email);
            member.setPassword(password);
            member.setMemberRole(MemberRole.ROLE_USER);
            return member;
        }

        private Board createBoard(String area, String content, Member member) {
            Set<BoardTag> boardTags = new HashSet<>();
            Board board = new Board();
            board.setArea(area);
            board.setContent(content);
            board.setBoardTags(boardTags);
            board.setMember(member);
            return board;
        }

        private Tag createTag(String _tag) {
            Tag tag = new Tag();
            tag.setTag(_tag);
            return tag;
        }

        private BoardTag createBoardTag(Board board, Tag tag) {
            BoardTag boardTag = new BoardTag();
            boardTag.setBoard(board);
            boardTag.setTag(tag);
            return boardTag;
        }

        private Comment createComment(Board board, Member member, String comment) {
            Comment createdComment = new Comment();
            createdComment.addMember(member);
            createdComment.addBoard(board);
            createdComment.setComment(comment);
            return createdComment;
        }
    }
}
