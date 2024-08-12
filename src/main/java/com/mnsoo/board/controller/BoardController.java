package com.mnsoo.board.controller;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.dto.PostRequestDto;
import com.mnsoo.board.dto.PostResponseDto;
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
            @RequestPart @Valid PostRequestDto postRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){

        boardService.writePost(postRequestDto, image);

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
            @RequestPart(required = false) @Valid PostRequestDto postRequestDto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ){

        boardService.updatePost(postId, postRequestDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.POST_UPDATE_SUCCESS,
                        "success"
                )
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<SuccessResponse<Page<PostResponseDto.Simple>>> getAllPosts(Pageable pageable) {

        Page<PostResponseDto.Simple> posts = boardService.getAllPosts(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_ALL_POSTS_SUCCESS,
                        posts
                )
        );
    }

    @GetMapping("/posts/search")
    public ResponseEntity<SuccessResponse<Page<PostResponseDto.Simple>>> getPostsByTitle(
            @RequestParam String title,
            Pageable pageable
    ) {

        Page<PostResponseDto.Simple> posts = boardService.getPostsByTitle(title, pageable);

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

    @GetMapping("/post/specific")
    public ResponseEntity<SuccessResponse<PostResponseDto.Specific>> getSpecificPost(@RequestParam Long postId) {

        PostResponseDto.Specific post = boardService.getSpecificPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.GET_SPECIFIC_POST_SUCCESS,
                        post
                )
        );
    }

    @PostMapping("/post/specific/like")
    public ResponseEntity<SuccessResponse<String>> setPostLike(@RequestParam Long postId) {

        boardService.setPostLike(postId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.SET_POST_LIKE_SUCCESS,
                        "success"
                )
        );
    }
}
