package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.CommentDto;
import com.sixman.fattle.dto.request.CommentRequest;

import java.util.List;

public interface BattleCommentRepositoryCustom {

    List<CommentDto> getCommentList(int boardCode);

    void registComment(CommentRequest request);

}
