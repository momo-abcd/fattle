package com.sixman.fattle.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoResponse {

    private int ranking;
    private String nickname;
    private float height;
    private float weight;
    private float goalWeight;
    private int calory;
    private int goalCalory;
    private int carbo;
    private int goalCarbo;
    private int protein;
    private int goalProtein;
    private int fat;
    private int goalFat;
    private int growthExp;
    private int stackExp;
    private String imgPath;

}
