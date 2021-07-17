package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.domain.Board;
import remind.special_day.domain.Comment;
import remind.special_day.domain.Member;
import remind.special_day.dto.comment.CommentAddRequestDto;
import remind.special_day.dto.comment.CommentResponseDto;
import remind.special_day.dto.comment.CommentUpdateRequestDto;
import remind.special_day.repository.BoardRepository;
import remind.special_day.repository.CommentRepository;
import remind.special_day.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    /**
     * Comment 조회 - board_id
     */
    public List<CommentResponseDto> findAllByBoardId(Long boardId) {
        return commentRepository.findAllByBoardId(boardId)
                .stream().map(CommentResponseDto::dto)
                .collect(Collectors.toList());
    }

    /**
     * Comment 조회 - member_id
     */
    public List<CommentResponseDto> findAllByMemberId(Long memberId) {
        return commentRepository.findAllByMemberId(memberId)
                .stream().map(CommentResponseDto::dto)
                .collect(Collectors.toList());
    }

    /**
     * Comment 추가
     */
    @Transactional
    public Long createComment(CommentAddRequestDto commentAddRequestDto) {
//        Long memberId = SecurityUtil.getCurrentMemberId();
        Long boardId = commentAddRequestDto.getBoardId();
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        Comment comment = commentAddRequestDto.dto(commentAddRequestDto);
        comment.addMember(member);
        comment.addBoard(board);
        commentRepository.save(comment);

        return comment.getId();
    }

    /**
     * Comment 수정
     */
    @Transactional
    public Long updateComment(CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(requestDto.getId()).orElseThrow(RuntimeException::new);

        comment.changeComment(requestDto.getComment());

        return comment.getId();
    }

    /**
     * Comment 삭제
     */
    @Transactional
    public Long deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        commentRepository.delete(comment);
        return commentId;
    }
}
