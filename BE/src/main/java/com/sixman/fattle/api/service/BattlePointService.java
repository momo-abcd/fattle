package com.sixman.fattle.api.service;

import com.sixman.fattle.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BattlePointService {

    private final BattleRepository battleRepository;

}
