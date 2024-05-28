package com.example.coordination.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CategoryType {
    TOPS("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("엑세서리"),
    ;

    private String value;

    @JsonCreator
    CategoryType(String name) {
        this.value = name;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static CategoryType findByName(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public static CategoryType findByValue(String value) {
        return Arrays.stream(values())
                .filter(it -> it.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
