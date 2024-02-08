package com.sixman.fattle.repository;

import com.sixman.fattle.dto.dto.BoardDto;

import java.util.List;

public interface BattleBoardRepositoryCustom {

    List<BoardDto> getBoardList(String battleCode);

}
