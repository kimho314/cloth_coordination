package com.example.coordination.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Category {
    TOPS("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스티커즈"),
    BAG("BAG"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("엑세서리"),
    ;

    private String value;

    @JsonCreator
    Category(String name) {
        this.value = name;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Category findByName(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public static Category findByValue(String value) {
        return Arrays.stream(values())
                .filter(it -> it.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}
