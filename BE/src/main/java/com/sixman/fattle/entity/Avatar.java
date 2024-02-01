package com.sixman.fattle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "avatar_tb")
@NoArgsConstructor
public class Avatar {

    @Id
    @Column(name = "avatar_cd")
    private String avatarCd;

    @Column(name = "level")
    private int level;

    @Column(name = "info")
    private String info;

    @Column(name = "img_path")
    private String imgPath;

}
