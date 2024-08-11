package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Post;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    List<Post> findByCreatedAt(LocalDate createdAt);

    Optional<Post> findByPostId(Long postId);

    // 생성일 기준 내림차순으로 모든 게시글 조회(페이징 사용)
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 제목 부분 검색 및 생성일 기준 내림차순으로 정렬(페이징 사용)
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:title% ORDER BY p.createdAt DESC")
    Page<Post> findByTitleContainingOrderByCreatedAtDesc(String title, Pageable pageable);
}
