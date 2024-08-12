package com.mnsoo.board.dto;

import com.mnsoo.board.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import java.time.Duration;

public class PostResponseDto {

    @Data
    @Builder
    public static class Simple {

        private Long postId;
        private String title;
        private int viewCount;
        private String author;
        private LocalDateTime createdAt;

        public static Simple fromEntity(Post post) {
            return Simple.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .viewCount(post.getViewCount())
                    .author(post.getUser().getName())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }

    @Data
    @Builder
    public static class Specific {

        private Long postId;
        private String title;
        private String content;
        private String imgUrl;
        private int viewCount;
        private int likeCount;
        private String author;
        private int leftDaysToEditable;
        private boolean editable;
        private LocalDateTime createdAt;

        public static Specific fromEntity(Post post) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime createdAt = post.getCreatedAt();
            long daysSinceCreated = Duration.between(createdAt, now).toDays();
            int leftDaysToEditable = (int) (9 - daysSinceCreated);

            boolean editable = true;
            if(leftDaysToEditable < 0) {
                editable = false;
                leftDaysToEditable = 0;
            }

            return Specific.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imgUrl(post.getImgUrl())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .author(post.getUser().getName())
                    .leftDaysToEditable(leftDaysToEditable)
                    .editable(editable)
                    .createdAt(createdAt)
                    .build();
        }
    }
}