package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import remind.special_day.dto.board.BoardListResponseDto;
import remind.special_day.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/main")
    public ResponseEntity<List<BoardListResponseDto>> findAll() {
        return ResponseEntity.ok(boardService.findAllBoard());
    }

    @GetMapping("/main/{tag}")
    public ResponseEntity<List<BoardListResponseDto>> findByTag(@PathVariable("tag") String tag) {
        return ResponseEntity.ok(boardService.findBoardByTag(tag));
    }
}
