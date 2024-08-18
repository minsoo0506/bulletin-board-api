package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Post;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    Optional<Post> findByPostIdAndDeletedAtIsNull(Long postId);

    @Query("SELECT p FROM Post p WHERE p.createdAt = :nineDaysAgo AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAtAndDeletedAtIsNull(@Param("nineDaysAgo") LocalDate nineDaysAgo);

    // 생성일 기준 내림차순으로 삭제되지 않은 모든 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 제목 부분 검색 및 생성일 기준 내림차순으로 소프트 삭제되지 않은 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title% AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    Page<Post> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
}
