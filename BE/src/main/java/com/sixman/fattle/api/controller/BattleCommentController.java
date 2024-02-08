package com.sixman.fattle.api.controller;

import com.sixman.fattle.api.service.BattleCommentService;
import com.sixman.fattle.dto.response.CommentListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/battle/comment")
@RequiredArgsConstructor
@Tag(name = "Battle Comment 컨트롤러", description = "배틀 식단 댓글을 위한 API")
public class BattleCommentController {

    private final BattleCommentService commentService;

    @Operation(summary = "배틀 식단 댓글 리스트",
            description = "배틀 식단 댓글 리스트 조회")
    @ApiResponse(responseCode = "200", description = "댓글 리스트 조회 성공")
    @GetMapping("/list/{boardCode}")
    public ResponseEntity<CommentListResponse> getCommentList(@PathVariable int boardCode) {
        CommentListResponse response = commentService.getCommentList(boardCode);
        return ResponseEntity.ok(response);
    }

}
