package com.mnsoo.board.controller;

import com.mnsoo.board.dto.comment.CommentRequestDto;
import com.mnsoo.board.dto.comment.CommentResponseDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.CommentService;
import com.mnsoo.board.type.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "게시글에 대한 댓글 작성")
    @PostMapping
    public ResponseEntity<SuccessResponse<String>> writeComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {

        commentService.writeComment(commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.COMMENT_WRITE_SUCCESS,
                        "success"
                )
        );
    }

    @Operation(summary = "댓글 수정", description = "게시글에 대한 댓글 수정")
    @PutMapping
    public ResponseEntity<SuccessResponse<String>> updateComment(@RequestBody @Valid CommentRequestDto commentRequestDto) {

        commentService.updateComment(commentRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.COMMENT_UPDATE_SUCCESS,
                        "success"
                )
        );
    }

    @Operation(summary = "댓글 조회", description = "게시글에 대한 댓글 리스트 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse<List<CommentResponseDto>>> getComments(@RequestParam Long postId){

        List<CommentResponseDto> comments = commentService.getComments(postId);

        if(comments == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_COMMENTS_SUCCESS,
                        comments
                )
        );
    }

    @Operation(summary = "댓글 삭제", description = "게시글에 대해 작성한 댓글 삭제")
    @DeleteMapping
    public ResponseEntity<SuccessResponse<String>> deleteComment(@RequestParam Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.COMMENT_DELETE_SUCCESS,
                        "success"
                )
        );
    }
}
