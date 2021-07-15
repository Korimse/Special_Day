package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.dto.board.BoardListResponseDto;
import remind.special_day.dto.board.BoardResponseDto;
import remind.special_day.repository.BoardRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

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
    public List<BoardListResponseDto> findBoardByTag(List<String> tag) {
        return null;
    }

    /**
     * Id를 통해 BoardList 조회
     */
    public List<BoardListResponseDto> findBoardById(Long id) {
        return null;
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
