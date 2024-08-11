package com.mnsoo.board.controller;

import com.mnsoo.board.dto.PostDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.BoardService;
import com.mnsoo.board.type.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/post/write")
    public ResponseEntity<SuccessResponse<String>> writePost(
            @RequestPart @Valid PostDto postDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){

        boardService.writePost(postDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.POST_WRITE_SUCCESS,
                        "success"
                )
        );
    }

    @PutMapping("/post/update")
    public ResponseEntity<SuccessResponse<String>> updatePost(
            @RequestParam Long postId,
            @RequestPart(required = false) @Valid PostDto postDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){

        boardService.updatePost(postId, postDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.POST_UPDATE_SUCCESS,
                        "success"
                )
        );
    }
}
