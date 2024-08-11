package com.mnsoo.board.controller;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.dto.PostDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.BoardService;
import com.mnsoo.board.type.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/posts")
    public ResponseEntity<SuccessResponse<Page<Post>>> getAllPosts(Pageable pageable) {

        Page<Post> posts = boardService.getAllPosts(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_ALL_POSTS_SUCCESS,
                        posts
                )
        );
    }

    @GetMapping("/posts/search")
    public ResponseEntity<SuccessResponse<Page<Post>>> getPostsByTitle(
            @RequestParam String title,
            Pageable pageable
    ) {

        Page<Post> posts = boardService.getPostsByTitle(title, pageable);

        if(posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_POSTS_BY_SEARCH_SUCCESS,
                        posts
                )
        );
    }
}
