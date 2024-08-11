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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final S3Service s3Service;

    public BoardService(UserService userService, UserRepository userRepository, PostRepository postRepository,
                        EmailNotificationService emailNotificationService, S3Service s3Service) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.emailNotificationService = emailNotificationService;
        this.s3Service = s3Service;
    }

    /**
     * 유저가 작성한 게시글을 등록
     *
     * @param postDto : 제목과 내용은 필수값
     * @param image : (임시)기본적으로 게시글당 하나의 이미지 파일만 업로드 (image 엔티티 사용하여 여러개의 파일 업로드 가능하도록 할 예정)
     */
    public void writePost(PostDto postDto, MultipartFile image){

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

        if(image != null) {
            post.setImgUrl(s3Service.uploadPostImage(image));
        }

        postRepository.save(post);

        log.info("Posted successfully");
    }

    /**
     * 매일 자정에 게시글 작성(등록) 날짜 확인 후 유저에게 메일을 전송
     * 생성일 9일째 경고 알림(하루 후 수정 불가 알람) : 게시글은 생성일 기준 10일 이후 수정불가
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkPostsForWarnings() {

        log.info("Finding posts created 9 days ago");

        LocalDate nineDaysAgo = LocalDate.now().minusDays(9);
        List<Post> posts = postRepository.findByCreatedAt(nineDaysAgo);

        for(Post post : posts){
            emailNotificationService.sendWarning(post.getUser(), post.getTitle());
        }

        log.info("Sent '{}' warning mail", posts.size());
    }

    /**
     * 유저가 올린 게시글을 수정
     *
     * @param postId : 수정하고자 하는 게시글의 id
     * @param postDto : 제목, 내용
     * @param image : 이미지 파일
     */
    public void updatePost(Long postId, PostDto postDto, MultipartFile image){

        User user = userRepository.findByEmail(userService.getCurrentUserEmail())
                .orElseThrow(() -> new RestApiException(ErrorCode.FORBIDDEN));

        log.info("Updating post of user '{}'", user.getLoginId());

        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new RestApiException(ErrorCode.POST_NOT_FOUND));

        if(user.getUserId() != post.getUser().getUserId()) {
            log.warn("Author and user not match");
            throw new RestApiException(ErrorCode.AUTHOR_AND_USER_NOT_MATCH);
        }

        if(postDto == null && image == null) {
            log.warn("There is nothing to update for post '{}'", post.getPostId());
            throw new RestApiException(ErrorCode.NOTHING_TO_UPDATE);
        }

        if(postDto != null){
            if(postDto.getTitle() != null) post.setTitle(post.getTitle());
            if(postDto.getContent() != null) post.setContent(post.getContent());
        }

        if(image != null){
            post.setImgUrl(s3Service.uploadPostImage(image));
        }

        postRepository.save(post);

        log.info("Post '{}' updated", post.getPostId());
    }

    /**
     * 생성일 기준으로 내림차순 페이징 처리된 게시판 목록 반환
     *
     * @param pageable : 페이징 처리시 필요 (RequestParam으로 넘겨받은 page, size가 JPA에 의해 자동으로 Pageable로 변환됨)
     * @return Page<Post>
     */
    public Page<Post> getAllPosts(Pageable pageable) {

        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /**
     * 검색된 문자열이 제목에 포함된 게시글들을 반환(생성일 기준 내림차순)
     *
     * @param title : 검색된 문자열
     * @param pageable : 페이징 처리시 필요
     * @return Page<Post>
     */
    public Page<Post> getPostsByTitle(String title, Pageable pageable) {

        return postRepository.findByTitleContainingOrderByCreatedAtDesc(title, pageable);
    }
}
