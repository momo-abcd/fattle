package com.sixman.fattle.api.service;

import com.sixman.fattle.dto.dto.BoardDto;
import com.sixman.fattle.dto.response.BoardListResponse;
import com.sixman.fattle.repository.BattleBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleBoardService {

    private final BattleBoardRepository boardRepository;

    public BoardListResponse getBoardList(String battleCode) {
        List<BoardDto> list = boardRepository.getBoardList(battleCode);

        return BoardListResponse.builder()
                .list(list)
                .build();
    }

}
