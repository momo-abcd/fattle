package com.sixman.fattle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "food_board_tb")
public class FoodBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_board_cd")
    private int foodBoardCd;

    @Column(name = "battle_cd")
    private String battleCd;

    @Column(name = "player_cd")
    private long playerCd;

    @Column(name = "rec_dt")
    private Timestamp recDt;

    @Column(name = "img_path")
    private String imgPath;

}
