package com.mnsoo.board.controller;

import com.mnsoo.board.dto.post.PostRequestDto;
import com.mnsoo.board.dto.post.PostResponseDto;
import com.mnsoo.board.dto.SuccessResponse;
import com.mnsoo.board.service.BoardService;
import com.mnsoo.board.type.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @Operation(summary = "게시글 작성", description = "회원의 권한으로 게시글 작성")
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

    @Operation(summary = "게시글 수정", description = "게시글 수정 (작성 이후 10일이 지나지 않은 게시글만 수정 가능)")
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

    @Operation(summary = "게시글 목록 조회", description = "페이징 처리된 게시글 목록 조회")
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

    @Operation(summary = "게시글 검색", description = "입력된 검색어가 제목에 포함된 게시글 조회")
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

    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회 및 조회수 증가")
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

    @Operation(summary = "게시글 좋아요", description = "게시글의 좋아요 및 취소 설정")
    @PostMapping("/post/specific/like")
    public ResponseEntity<SuccessResponse<String>> setPostLike(@RequestParam Long postId) {

        boolean isLiked = boardService.setPostLike(postId);

        if(isLiked) {

            return ResponseEntity.status(HttpStatus.OK).body(
                    SuccessResponse.of(
                            ResponseMessage.SET_POST_LIKE_SUCCESS,
                            "success"
                    )
            );
        } else {

            return ResponseEntity.status(HttpStatus.OK).body(
                    SuccessResponse.of(
                            ResponseMessage.SET_POST_LIKE_CANCEL_SUCCESS,
                            "success"
                    )
            );
        }
    }

    @Operation(summary = "게시글 삭제", description = "작성자 권한으로 게시글 삭제")
    @DeleteMapping("/post/delete")
    public ResponseEntity<SuccessResponse<String>> deletePost(@RequestParam Long postId) {

        boardService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.of(
                        ResponseMessage.POST_DELETE_SUCCESS,
                        "success"
                )
        );
    }
}
