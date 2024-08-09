package com.mnsoo.board.service;

import com.mnsoo.board.domain.Post;
import com.mnsoo.board.domain.User;
import com.mnsoo.board.dto.PostDto;
import com.mnsoo.board.exception.RestApiException;
import com.mnsoo.board.repository.PostRepository;
import com.mnsoo.board.repository.UserRepository;
import com.mnsoo.board.type.ErrorCode;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class BoardService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final EmailNotificationService emailNotificationService;

    public BoardService(UserService userService, UserRepository userRepository, PostRepository postRepository,
                        EmailNotificationService emailNotificationService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.emailNotificationService = emailNotificationService;
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

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 게시글 작성 날짜 확인 후 메일 전송
    public void checkPostsForWarnings() {

        log.info("Finding posts created 9 days ago");

        LocalDate nineDaysAgo = LocalDate.now().minusDays(9);
        List<Post> posts = postRepository.findByCreatedAt(nineDaysAgo);

        for(Post post : posts){
            emailNotificationService.sendWarning(post.getUser(), post.getTitle());
        }

        log.info("Sent '{}' warning mail", posts.size());
    }
}
