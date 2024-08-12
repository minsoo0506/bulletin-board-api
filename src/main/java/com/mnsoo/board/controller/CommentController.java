package com.mnsoo.board.controller;

import com.mnsoo.board.dto.CommentRequestDto;
import com.mnsoo.board.dto.CommentResponseDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.CommentService;
import com.mnsoo.board.type.ResponseMessage;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<SuccessResponse<List<CommentResponseDto>>> getComments(@RequestParam Long postId){

        List<CommentResponseDto> comments = commentService.getComments(postId);

        if(comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_COMMENTS_SUCCESS,
                        comments
                )
        );
    }
}
