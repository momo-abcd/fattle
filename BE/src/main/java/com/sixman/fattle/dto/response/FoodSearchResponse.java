package com.sixman.fattle.dto.response;

import com.sixman.fattle.dto.dto.FoodSearch;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FoodSearchResponse {

    private List<FoodSearch> list;

}
