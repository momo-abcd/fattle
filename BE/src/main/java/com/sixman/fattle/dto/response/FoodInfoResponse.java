package com.sixman.fattle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodInfoResponse {

    private String food_cd;

    private String name;

    private float gram;

    private int calory;

    private int carbo;

    private int protein;

    private int fat;

}
