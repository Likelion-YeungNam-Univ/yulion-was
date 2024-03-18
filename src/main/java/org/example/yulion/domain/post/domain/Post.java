package org.example.yulion.domain.post.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.example.yulion.domain.post.domain.Category;
import org.example.yulion.domain.post.domain.Part;
import org.example.yulion.domain.common.BaseTimeEntity;
import org.example.yulion.domain.user.domain.User;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User writer;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Part part;

    @Column(nullable = false)
    private String title;

    private String content;

    @ColumnDefault("0")
    private Long viewCnt;

    @Column(nullable = true)
    private String members;

    @Column(nullable = true)
    private String mentor;

    @Enumerated(EnumType.STRING)

    @ColumnDefault("'ACTIVE'")
    private PostStatus status;

    private LocalDateTime deletedAt;

    @Builder
    private Post(String title, String content, Long viewCnt, String members, User writer, Category category, Part part, String mentor){
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.members = members;
        this.writer = writer;
        this.category = category;
        this.part = part;
        this.mentor = mentor;
    }

    // 연관관계 매핑 메소드
//    public void setWriter(User user){
//        this.writer = user;
//        user.getPosts().add(this);
//    }
    public void modifyPost(String title, String content, String members, Category category, Part part){
        this.title = title;
        this.content = content;
        this.members = members;
        this.category = category;
        this.part = part;
    }

    public void deletePost(){
        this.status = PostStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

}
