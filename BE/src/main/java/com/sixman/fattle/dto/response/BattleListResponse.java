package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.dto.BattleInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BattleListResponse {

    private List<BattleInfo> list;

}
