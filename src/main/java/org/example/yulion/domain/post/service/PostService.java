package org.example.yulion.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.example.yulion.domain.post.domain.Category;
import org.example.yulion.domain.post.domain.Part;
import org.example.yulion.domain.post.domain.Post;
import org.example.yulion.domain.post.dto.request.PostCreateRequest;
import org.example.yulion.domain.post.dto.response.*;
import org.example.yulion.domain.post.exception.PostException;
import org.example.yulion.domain.post.exception.PostExceptionType;
import org.example.yulion.domain.post.repository.PostRepository;
import org.example.yulion.domain.user.domain.User;
import org.example.yulion.domain.user.repository.UserRepository;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 1. Controller => createPost 메서드에 매핑되는 Service 로직
    public PostDetailResponse addPost(PostCreateRequest request, Long userId) {
        // 인증 구현전 테스팅용 유저 정보 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(PostExceptionType.WRITER_NOT_FOUND));

        Post post = createPost(request, user);
        Post savedPost = postRepository.save(post);

        return PostDetailResponse.from(savedPost);
    }

    public Post createPost(final PostCreateRequest request, final User user) {
        Category category = Category.valueOf(request.getCategory());
        Part part = Part.valueOf(request.getPart());

        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .members(request.getMembers())
                .part(part)
                .category(category)
                .writer(user)
                .build();
    }

    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));

        return PostDetailResponse.from(post);
    }

    public PostDetailResponse modifyPost(Long id, PostCreateRequest request, Long userId) {
        Category category = Category.valueOf(request.getCategory());
        Part part = Part.valueOf(request.getPart());

        Post post = CheckPostOwner(id, userId);

        post.modifyPost(request.getTitle(), request.getContent(), request.getMembers(), category, part);
        final Post savedPost = postRepository.save(post);

        return PostDetailResponse.from(savedPost);
    }

    public PostDetailResponse deletePost(Long id, Long userId) {
        Post post = CheckPostOwner(id, userId);
        postRepository.delete(post);

        return PostDetailResponse.from(post);
    }

    @Transactional
    public PostCommonListResponse getCommonList(Pageable pageable, Category category){
        Page<Post> posts = postRepository.findAllByCategoryOrderByCreateAt(category, pageable);

        List<PostCommonSummaryResponse> postResponses = posts.getContent().stream()
                .map(PostCommonSummaryResponse::from)
                .collect(Collectors.toList());

        return PostCommonListResponse.from(postResponses, posts.getNumber(), posts.getTotalPages());
    }

    @Transactional
    public PostEducationListResponse getEducationList(Pageable pageable, Category category){
        Page<Post> posts = postRepository.findAllByCategoryOrderByCreateAt(category, pageable);

        List<PostEducationSummaryResponse> postResponses = posts.getContent().stream()
                .map(PostEducationSummaryResponse::from)
                .collect(Collectors.toList());

        return PostEducationListResponse.from(postResponses, posts.getNumber(), posts.getTotalPages());
    }

    public Post CheckPostOwner(Long postId, Long userId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostExceptionType.POST_NOT_FOUND));
        if (!post.getWriter().getId().equals(userId)) {
            throw new PostException(PostExceptionType.UNAUTHORIZED_POST);
        }
        return post;
    }
}
