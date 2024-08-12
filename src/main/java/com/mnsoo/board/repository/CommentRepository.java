package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Comment;
import com.mnsoo.board.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long commentId);

    List<Comment> findByPostOrderByCreatedAt(Post post);
}
