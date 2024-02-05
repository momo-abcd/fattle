package com.sixman.fattle.dto.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankingInfo {
    private int rank;
    private long userCode;
    private String nickName;
    private int growthExp;
    private int stackExp;
    private String imgPath;

}
