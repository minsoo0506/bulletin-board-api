package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Post;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    List<Post> findByCreatedAt(LocalDate createdAt);

    Optional<Post> findByPostId(Long postId);
}
