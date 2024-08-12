package com.mnsoo.board.controller;

import com.mnsoo.board.dto.CommentDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.CommentService;
import com.mnsoo.board.type.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<SuccessResponse<String>> writeComment(@RequestBody @Valid CommentDto commentDto) {

        commentService.writeComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.COMMENT_WRITE_SUCCESS,
                        "success"
                )
        );
    }
}
