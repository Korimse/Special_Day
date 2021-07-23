package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import remind.special_day.domain.Board;
import remind.special_day.dto.board.BoardAddRequestDto;
import remind.special_day.dto.board.BoardListResponseDto;
import remind.special_day.dto.board.BoardResponseDto;
import remind.special_day.dto.board.BoardUpdateRequestDto;
import remind.special_day.service.BoardService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/main")
    public ResponseEntity<List<BoardListResponseDto>> findAll() {
        return ResponseEntity.ok(boardService.findAllBoard());
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<BoardListResponseDto>> findByTag(@PathVariable("tag") String tag) {
        return ResponseEntity.ok(boardService.findBoardByTag(tag));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<BoardListResponseDto>> findByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(boardService.findBoardById(email));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<BoardListResponseDto>> findByArea(@PathVariable("area") String area) {
        return ResponseEntity.ok(boardService.findBoardByArea(area));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<List<BoardResponseDto>> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.findBoardByBoardId(id));
    }

    @PostMapping(value = "/write")
    public ResponseEntity<Long> addBoard(@ModelAttribute BoardAddRequestDto boardAddRequestDto) {
        return ResponseEntity.ok(boardService.addBoard(boardAddRequestDto));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Long> updateBoard(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto, @PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.UpdateBoard(id, boardUpdateRequestDto));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteBoard(@PathVariable("id") Long id) {
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }
}
