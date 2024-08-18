package com.mnsoo.board.repository;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.PostLike;
import com.mnsoo.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    PostLike findByPostAndUser(Post post, User user);
}
