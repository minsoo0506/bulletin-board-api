package com.mnsoo.board.service;

import com.mnsoo.board.domain.Comment;
import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.CommentDto;
import com.mnsoo.board.exception.RestApiException;
import com.mnsoo.board.repository.CommentRepository;
import com.mnsoo.board.repository.PostRepository;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository,
                          UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * 댓글 등록
     *
     * @param commentDto : 게시글의 id, 댓글 정보를 담는 Dto
     */
    public void writeComment(CommentDto commentDto) {

        log.info("Registering comment of post '{}'", commentDto.getPostId());

        String userEmail = userService.getCurrentUserEmail();

        if(userEmail == null) {
            log.warn("Need to login first for write comment");
            throw new RestApiException(ErrorCode.FAILED_TO_WRITE_COMMENT);
        } else {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RestApiException(ErrorCode.NOT_AUTHENTICATED_USER));

            Post post = postRepository.findByPostId(commentDto.getPostId())
                    .orElseThrow(() -> new RestApiException(ErrorCode.POST_NOT_FOUND));

            Comment comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .content(commentDto.getContent())
                    .build();

            commentRepository.save(comment);

            log.info("Comment Registered");
        }
    }
}
