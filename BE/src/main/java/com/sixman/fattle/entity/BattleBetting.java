package com.sixman.fattle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "battle_betting_tb")
public class BattleBetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "betting_cd")
    private int bettingCd;

    @Column(name = "battle_cd")
    private String battleCd;

    @Column(name = "content")
    private String content;

}
