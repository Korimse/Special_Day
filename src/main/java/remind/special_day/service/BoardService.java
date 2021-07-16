package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.dto.board.BoardListResponseDto;
import remind.special_day.dto.board.BoardResponseDto;
import remind.special_day.dto.tag.TagResponseDto;
import remind.special_day.repository.BoardRepository;
import remind.special_day.repository.BoardRepositorySupport;
import remind.special_day.repository.TagRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRepositorySupport boardRepositorySupport;

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
     * Tag를 통해 BoardList 조회
     */
    public List<BoardListResponseDto> findBoardByTag(String tag) {
        return boardRepositorySupport.findByTag(tag)
                .stream()
                .map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * Id를 통해 BoardList 조회
     */
    public List<BoardListResponseDto> findBoardById(String email) {
        return boardRepositorySupport.findByMember(email)
                .stream()
                .map(BoardListResponseDto::dto)
                .sorted(Comparator.comparing(BoardListResponseDto::getId, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    /**
     * Area를 통해 BoardList 조회
     */
    public List<BoardListResponseDto> findBoardByArea(List<String> area) {
        return null;
    }

    /**
     * board_id를 통해 Board 조회
     */
    public BoardResponseDto findBoardByBoardId(Long id) {
        return null;
    }


}
