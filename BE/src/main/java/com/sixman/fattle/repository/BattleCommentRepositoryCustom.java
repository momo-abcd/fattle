package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.CommentDto;

import java.util.List;

public interface BattleCommentRepositoryCustom {

    List<CommentDto> getCommentList(int boardCode);

}
