package com.sixman.fattle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "food_comment_tb")
public class FoodComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_comment_cd")
    private int foodCommentCd;

    @Column(name = "food_board_cd")
    private int foodBoardCd;

    @Column(name = "trigger_cd")
    private long triggerCd;

    @Column(name = "content")
    private String content;

    @Column(name = "rec_dt")
    private Timestamp recDt;

}
