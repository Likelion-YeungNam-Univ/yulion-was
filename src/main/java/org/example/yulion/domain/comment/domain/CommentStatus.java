package org.example.yulion.domain.comment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CommentStatus {
    ACTIVE("게시 중"),
    DELETED("삭제 됨"),
    ;
    private final String title;
}
