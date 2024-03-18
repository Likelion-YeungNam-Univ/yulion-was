package org.example.yulion.domain.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.yulion.domain.common.BaseTimeEntity;
import org.example.yulion.domain.post.domain.PostStatus;
import org.example.yulion.domain.user.domain.User;
import org.example.yulion.domain.post.domain.Post;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@ToString
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "parent_id")
    @Builder.Default
    private Comment parentComment = null;

    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<Comment>();

    @Builder.Default
    private CommentStatus status = CommentStatus.ACTIVE;

    private LocalDateTime deletedAt;

    @Builder
    private Comment(User writer, Post post, Comment parentComment, String content, CommentStatus status){
        this.writer = writer;
        this.post = post;
        this.parentComment = parentComment;
        this.content = content;
        this.status = status;
    }

    public void modifyContent(String content) {
        this.content = content;
    }

    public void deleteComment(){
        this.status = CommentStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }
}
