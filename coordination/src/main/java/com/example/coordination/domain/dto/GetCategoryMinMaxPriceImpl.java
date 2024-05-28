package com.example.coordination.domain.dto;

import com.example.coordination.domain.enums.CategoryType;

public interface GetCategoryMinMaxPriceImpl {
    CategoryType getCategory();

    String getBrandName();

    Integer getPrice();
}
