package com.mnsoo.board.repository;

import com.mnsoo.board.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    boolean existsByLoginId (String loginId);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByEmail(String email);
}
