package com.sixman.fattle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "food_tb")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_cd")
    private int foodCd;

    @Column(name = "user_cd")
    private long userCd;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "calory")
    private int calory;

    @Column(name = "carbo")
    private int carbo;

    @Column(name = "protein")
    private int protein;

    @Column(name = "fat")
    private int fat;

    @Column(name = "rec_dt")
    private Timestamp recDt;

    @Column(name = "img_path")
    private String imgPath;

}
