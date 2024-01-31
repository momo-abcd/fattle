package com.sixman.fattle.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
public abstract class BattleUser {

    @Id
    @JoinColumn(name = "battle_cd", referencedColumnName = "battle_cd")
    private String battleCd;

    @Id
    @JoinColumn(name = "user_cd", referencedColumnName = "user_cd")
    private long userCd;

}
