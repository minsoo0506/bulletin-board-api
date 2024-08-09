package com.mnsoo.board.service;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.PostDto;
import com.mnsoo.board.exception.RestApiException;
import com.mnsoo.board.repository.PostRepository;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BoardService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public BoardService(UserService userService, UserRepository userRepository, PostRepository postRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void write(PostDto postDto, MultipartFile image){

        User user = userRepository.findByEmail(userService.getCurrentUserEmail())
                .orElseThrow(() -> new RestApiException(ErrorCode.FORBIDDEN));

        log.info("Registering post of user '{}'", user.getLoginId());

        if(postDto == null || postDto.getTitle() == null || postDto.getContent() == null){
            log.warn("Required fields : title, content");
            throw new RestApiException(ErrorCode.REQUIRED_FIELD_NULL);
        }

        Post post = Post.builder()
                .user(user)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();

        postRepository.save(post);

        log.info("Posted successfully");
    }
}
