package com.sixman.fattle.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpRepositoryImpl {

    private final JPAQueryFactory queryFactory;



}
