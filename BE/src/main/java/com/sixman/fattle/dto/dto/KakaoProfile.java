package com.sixman.fattle.dto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class KakaoProfile {

    private Map<String, Object> attributes;

    public KakaoProfile(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public long getProviderId() {
        return Long.parseLong(String.valueOf(attributes.get("id")));
    }

}
