package com.mnsoo.board.service;

import com.mnsoo.board.domain.Comment;
import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.comment.CommentRequestDto;
import com.mnsoo.board.dto.comment.CommentResponseDto;
import com.mnsoo.board.exception.RestApiException;
import com.mnsoo.board.repository.CommentRepository;
import com.mnsoo.board.repository.PostRepository;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.type.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
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
     * @param commentRequestDto : 게시글의 id, 댓글 정보를 담는 Dto
     */
    public void writeComment(CommentRequestDto commentRequestDto) {

        log.info("Registering comment of post '{}'", commentRequestDto.getPostId());

        String userEmail = userService.getCurrentUserEmail();

        if(userEmail == null) {
            log.warn("Need to login first for write comment");
            throw new RestApiException(ErrorCode.FAILED_TO_WRITE_COMMENT);
        } else {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RestApiException(ErrorCode.NOT_AUTHENTICATED_USER));

            Post post = postRepository.findByPostIdAndDeletedAtIsNull(commentRequestDto.getPostId())
                    .orElseThrow(() -> new RestApiException(ErrorCode.POST_NOT_FOUND));

            Comment comment = Comment.builder()
                    .post(post)
                    .user(user)
                    .content(commentRequestDto.getContent())
                    .build();

            commentRepository.save(comment);

            log.info("Comment Registered");
        }
    }

    /**
     * 댓글 수정
     *
     * @param commentRequestDto : comment id와 수정할 내용은 필수값
     */
    public void updateComment(CommentRequestDto commentRequestDto) {

        if(commentRequestDto.getCommentId() == null) {
            log.warn("Comment id is required");
            throw new RestApiException(ErrorCode.REQUIRED_PARAMETER_NULL);
        }

        log.info("Updating comment : comment_id '{}'", commentRequestDto.getCommentId());

        if(commentRequestDto.getContent() != null){
            Comment comment = commentRepository.findByCommentIdAndDeletedAtIsNull(commentRequestDto.getCommentId())
                    .orElseThrow(() -> new RestApiException(ErrorCode.COMMENT_NOT_FOUND));

            comment.setContent(commentRequestDto.getContent());

            commentRepository.save(comment);
        }
    }

    /**
     * 해당 게시물에 대한 댓글 목록 조회
     *
     * @param postId : 게시물의 id
     * @return List<CommentResponseDto> : 댓글 리스트
     */
    public List<CommentResponseDto> getComments(Long postId) {

        Post post = postRepository.findByPostIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new RestApiException(ErrorCode.POST_NOT_FOUND));

        log.info("Getting comment list of post : post_id '{}'", postId);

        List<Comment> comments = commentRepository.findALLByPostOrderByCreatedAt(post);

        if(comments.isEmpty()) {
            log.info("No comments for this post : post_id '{}'", postId);
            return null;
        } else {
            return comments
                    .stream()
                    .map(CommentResponseDto::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    /**
     * 댓글 삭제(soft delete)
     *
     * @param commentId : 삭제하고자 하는 댓글의 id
     */
    public void deleteComment(Long commentId) {

        log.info("Deleting comment : comment_id '{}'", commentId);

        Comment comment = commentRepository.findByCommentIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }
}
