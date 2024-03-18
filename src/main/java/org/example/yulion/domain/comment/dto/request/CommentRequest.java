package org.example.yulion.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.example.yulion.domain.comment.domain.Comment;

public record CommentRequest (
        @NotBlank String content,
        Comment parentComment
){
}
