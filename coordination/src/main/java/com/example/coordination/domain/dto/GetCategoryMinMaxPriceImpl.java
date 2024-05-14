package com.example.coordination.domain.dto;

import com.example.coordination.domain.enums.Category;

public interface GetCategoryMinMaxPriceImpl {
    Category getCategory();

    String getBrandName();

    Integer getPrice();
}
