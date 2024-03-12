package org.example.yulion.domain.comment.repository;

import org.example.yulion.domain.comment.domain.Comment;
import org.example.yulion.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
        Page<Comment> findAllByWriterOrderByCreatedAt(User user, Pageable pageable);
}
