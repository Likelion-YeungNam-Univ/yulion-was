package org.example.yulion.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.yulion.domain.comment.dto.request.CommentRequest;
import org.example.yulion.domain.comment.dto.response.CommentListResponse;
import org.example.yulion.domain.comment.dto.response.CommentResponse;
import org.example.yulion.domain.comment.service.CommentService;
import org.example.yulion.global.auth.userdetails.CustomUserDetails;
import org.example.yulion.global.config.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/comments")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "Comment", description = "댓글 관련 API")
public class CommentController {
/*
     필요한 API
     1. 댓글 생성 (어디에? -> 게시글 id)
     2. 게시물별 댓글 조회 (어디꺼? -> 게시글 id)
     3. 댓글 수정
     4. 댓글 삭제

     5. 대댓글 작성
*/
    private final CommentService commentService;

    @Operation(summary = "댓글 생성")
    @PostMapping("/{id}")
    public ApiResponse<CommentResponse> createComment(@RequestBody CommentRequest request,
                                                      @PathVariable Long postId,
                                                      @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        CommentResponse response = commentService.addComment(request, postId, customUserDetails);

        return ApiResponse.createSuccess(response);
    }

    @Operation(summary = "게시물별 댓글 조회")
    @GetMapping("/{id}")
    public ApiResponse<CommentListResponse> getCommentList(Pageable pageable,
                                                       @PathVariable Long postId,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails){
        CommentListResponse response = commentService.getCommentList(pageable, postId, customUserDetails);

        return ApiResponse.createSuccess(response);
    }

    @Operation(summary = "댓글 수정")
    @GetMapping("/{id}")
    public ApiResponse<CommentResponse> modifyComment(@RequestBody CommentRequest request,
                                                      @PathVariable Long commentId,
                                                      @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        CommentResponse response = commentService.modifyComment(request, commentId, customUserDetails);

        return ApiResponse.createSuccess(response);
    }

    @Operation(summary = "대댓글 작성")
    @PostMapping("/{id}/reply")
    public ApiResponse<CommentResponse> createReply(@RequestBody CommentRequest request,
                                                    @PathVariable Long commentId,
                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        CommentResponse response = commentService.addReply(request, commentId, customUserDetails);

        return ApiResponse.createSuccess(response);
    }

}
