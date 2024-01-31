package com.sixman.fattle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "ranking_vw")
public class Ranking {

    @Id
    @Column(name = "rank")
    private int rank;

    @Column(name = "user_cd")
    private long userCd;

}
