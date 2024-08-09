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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/write")
    public ResponseEntity<SuccessResponse<String>> write(
            @RequestPart @Valid PostDto post,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){

        boardService.write(post, image);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.POST_WRITE_SUCCESS,
                        "success"
                )
        );
    }
}
