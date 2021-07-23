package remind.special_day.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import remind.special_day.domain.Comment;
import remind.special_day.dto.comment.CommentAddRequestDto;
import remind.special_day.dto.comment.CommentResponseDto;
import remind.special_day.dto.comment.CommentUpdateRequestDto;
import remind.special_day.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boardid/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> findByBoardId(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(commentService.findAllByBoardId(boardId));
    }

    @GetMapping("/memberid/{memberId}")
    public ResponseEntity<List<CommentResponseDto>> findByMemberId(@PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(commentService.findAllByMemberId(memberId));
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createComment(@RequestBody CommentAddRequestDto commentAddRequestDto) {
        return ResponseEntity.ok(commentService.createComment(commentAddRequestDto));
    }

    @PostMapping("/change")
    public ResponseEntity<Long> updateComment(@RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentUpdateRequestDto));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }
}
