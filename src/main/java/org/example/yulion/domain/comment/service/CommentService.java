package org.example.yulion.domain.comment.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yulion.domain.comment.domain.Comment;
import org.example.yulion.domain.comment.domain.CommentStatus;
import org.example.yulion.domain.comment.dto.response.CommentListResponse;
import org.example.yulion.domain.comment.exception.CommentException;
import org.example.yulion.domain.comment.exception.CommentExceptionType;
import org.example.yulion.domain.comment.repository.CommentRepository;
import org.example.yulion.domain.post.domain.Post;
import org.example.yulion.domain.post.repository.PostRepository;
import org.example.yulion.domain.user.domain.User;
import org.example.yulion.domain.user.repository.UserRepository;
import org.example.yulion.global.auth.userdetails.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.yulion.domain.comment.dto.request.CommentRequest;
import org.example.yulion.domain.comment.dto.response.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse addComment(CommentRequest request,
                                      Long postId,
                                      CustomUserDetails customUserDetails){
        User user = getUser(customUserDetails);
        Comment comment = createComment(request, postId, user);
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public CommentListResponse getCommentList(Pageable pageable,
                                              Long postId,
                                              CustomUserDetails customUserDetails){
        User user = getUser(customUserDetails);
        Page<Comment> comments = commentRepository.findAllByWriterOrderByCreatedAt(user, pageable);
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());

        return CommentListResponse.of(commentResponses, comments.getNumber(), comments.getTotalPages());
    }

    public CommentResponse modifyComment(CommentRequest request,
                                         Long commentId,
                                         CustomUserDetails customUserDetails){
        User user = getUser(customUserDetails);
        Comment comment = CheckCommentOwner(commentId, user);
        comment.modifyContent(request.content());

        final Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

    public CommentResponse deleteComment (Long commentId,
                                          CustomUserDetails customUserDetails
    ){
        User user = getUser(customUserDetails);
        Comment comment = CheckCommentOwner(commentId, user);
        comment.deleteComment();

        final Comment savedComment = commentRepository.save(comment);

        return CommentResponse.from(savedComment);
    }

public CommentResponse addReply(CommentRequest request,
                                Long commentId,
                                CustomUserDetails customUserDetails
){
        User user = getUser(customUserDetails);
        Comment reply = createReply(request, commentId, user);

        Comment savedComment = commentRepository.save(reply);

        return CommentResponse.from(savedComment);
}

    public User getUser(CustomUserDetails customUserDetails){
        User user = userRepository.findById(customUserDetails.getId())
                .orElseThrow(() -> new CommentException(CommentExceptionType.WRITER_NOT_FOUND));

        return user;
    }

    public Comment createComment(CommentRequest request, Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND));

        Comment comment = Comment.builder()
                .writer(user)
                .post(post)
                .parentComment(null)
                .content(request.content())
                .status(CommentStatus.ACTIVE)
                .build();

        return comment;
    }

    public Comment CheckCommentOwner(Long commentId, User user){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND));
        if (!comment.getWriter().getId().equals(user.getId())) {
            throw new CommentException(CommentExceptionType.UNAUTHORIZED_COMMENT);
        }

        return comment;
    }

    public Comment createReply(CommentRequest request, Long commentId, User user){
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentExceptionType.COMMENT_NOT_FOUND));
        // 대댓글을 작성하려고 하는 댓글이 대댓글 일때. 즉, 2 depth 이상일 때.
        if (parentComment.getParentComment() != null){
            throw new CommentException(CommentExceptionType.INVALID_REPLY);
        }
        Post post = parentComment.getPost();

        Comment comment = Comment.builder()
                .writer(user)
                .post(post)
                .parentComment(parentComment)
                .content(request.content())
                .status(CommentStatus.ACTIVE)
                .build();

        return comment;
    }


}
