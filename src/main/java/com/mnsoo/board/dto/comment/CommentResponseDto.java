package com.mnsoo.board.dto.comment;

import com.mnsoo.board.domain.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long postId;
    private Long commentId;
    private String author;
    private String content;
    private LocalDateTime lastModifiedDate;

    public static CommentResponseDto fromEntity(Comment comment) {

        return CommentResponseDto.builder()
                .postId(comment.getPost().getPostId())
                .commentId(comment.getCommentId())
                .author(comment.getUser().getName())
                .content(comment.getContent())
                .lastModifiedDate(comment.getUpdatedAt())
                .build();
    }
}
