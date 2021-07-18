package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.domain.Board;
import remind.special_day.domain.Member;
import remind.special_day.dto.board.BoardAddRequestDto;
import remind.special_day.dto.board.BoardListResponseDto;
import remind.special_day.dto.board.BoardResponseDto;
import remind.special_day.dto.board.BoardUpdateRequestDto;
import remind.special_day.dto.tag.TagResponseDto;
import remind.special_day.exception.member.EmailNotFound;
import remind.special_day.repository.BoardRepository;
import remind.special_day.repository.BoardRepositorySupport;
import remind.special_day.repository.MemberRepository;
import remind.special_day.repository.TagRepository;
import remind.special_day.util.SecurityUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRepositorySupport boardRepositorySupport;
    private final MemberRepository memberRepository;
    private final AlbumService albumService;
    private final TagService tagService;

    /**
     * Board 조회
     */
    public List<BoardListResponseDto> findAllBoard() {
        return boardRepository.findAll()
                .stream()
                .map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * BoardList 조회 by tag
     */
    public List<BoardListResponseDto> findBoardByTag(String tag) {
        return boardRepositorySupport.findByTag(tag)
                .stream()
                .map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * BoardList 조회 by email
     */
    public List<BoardListResponseDto> findBoardById(String email) {
        return boardRepositorySupport.findByMember(email)
                .stream()
                .map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * BoardList 조회 by area
     */
    public List<BoardListResponseDto> findBoardByArea(String area) {
        return boardRepositorySupport.findByArea(area)
                .stream().map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * Board 조회 by board_id
     */
    public List<BoardResponseDto> findBoardByBoardId(Long id) {
        return boardRepository.findById(id)
                .stream()
                .map(BoardResponseDto::dto)
                .collect(Collectors.toList());
    }

    /**
     * Board 추가
     */
    @Transactional
    public Long addBoard(BoardAddRequestDto requestDto) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        Member member = memberRepository.findById(currentMemberId)
                .orElseThrow(EmailNotFound::new);
        Board board = albumService.addBoardAlbums(requestDto);
        tagService.addBoardTag(board, requestDto.getTags());
        board.setMember(member);
        boardRepository.save(board);

        return board.getId();
    }

    /**
     * Board 수정
     */
    @Transactional
    public Long UpdateBoard(Long board_id, BoardUpdateRequestDto requestDto) {

        Board board = boardRepository.findById(board_id)
                .orElseThrow(RuntimeException::new);

        if(!requestDto.getContent().isEmpty()) {
            board.setContent(requestDto.getContent());
        }
        if(!requestDto.getTags().isEmpty()) {
            tagService.updateBoardTag(board, requestDto.getTags());
        }
        return board_id;
    }

    /**
     * Board 삭제
     */
    @Transactional
    public Long deleteBoard(Long boardId) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);
        if(!currentMemberId.equals(board.getMember().getId())){
            throw new RuntimeException();
        }
        boardRepository.delete(board);
        return boardId;
    }


}
