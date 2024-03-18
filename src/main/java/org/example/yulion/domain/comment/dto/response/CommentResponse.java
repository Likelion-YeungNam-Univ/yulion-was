package org.example.yulion.domain.comment.dto.response;

import lombok.Builder;
import lombok.Setter;
import org.example.yulion.domain.comment.domain.Comment;
import org.example.yulion.domain.comment.domain.CommentStatus;

import java.util.List;

@Setter
public class CommentResponse {
    private final Long id;
    private final Long userId;
    private final String content;
    private final Long parentCommentId;
    private final List<Comment> childComments;

    @Builder
    public CommentResponse(Long id, Long userId, String content, Long parentCommentId, List<Comment> childComments) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.childComments = childComments;
    }

    public static CommentResponse from(Comment comment) {
        CommentResponseBuilder builder = CommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getWriter().getId())
                .content(comment.getContent())
                .parentCommentId(comment.getParentComment().getId())
                .childComments(comment.getChildComments());

        if (comment.getStatus() == CommentStatus.DELETED) {
            builder.content("삭제된 댓글입니다.");
        }

        return builder.build();
    }
}
