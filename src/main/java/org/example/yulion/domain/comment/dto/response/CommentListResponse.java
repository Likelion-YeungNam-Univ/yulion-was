package org.example.yulion.domain.comment.dto.response;

import java.util.List;

public record CommentListResponse(
        List<CommentResponse> content,
        int currentPage,
        int totalPage
) {
    public static CommentListResponse of(
            List<CommentResponse> content,
            int currentPage,
            int totalPage) {
        return new CommentListResponse(content, currentPage, totalPage);
    }
}
