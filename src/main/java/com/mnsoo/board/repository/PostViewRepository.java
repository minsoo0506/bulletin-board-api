package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.PostView;
import com.mnsoo.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Long> {

    boolean existsByPostAndUser(Post post, User user);
}
