package com.sixman.fattle.api.service;

import com.sixman.fattle.repository.ExpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpService {

    private final ExpRepository expRepository;

}
