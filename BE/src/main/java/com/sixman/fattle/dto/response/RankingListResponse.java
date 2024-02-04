package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.dto.RankingInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RankingListResponse {
    private List<RankingInfo> rankingList;
    private int myRank;
    private boolean end;
}
