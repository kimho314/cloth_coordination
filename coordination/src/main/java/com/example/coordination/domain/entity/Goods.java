package com.example.coordination.domain.entity;

import com.example.coordination.domain.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "GOODS")
@NoArgsConstructor
@AllArgsConstructor
public class Goods extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "BRAND_NAME", length = 1000)
    private String brandName;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    @Column(name = "PRICE")
    private Integer price;


    public static List<Goods> createDefaultWithBrandName(String brandName) {
        Goods goods1 = Goods.builder()
                .brandName(brandName)
                .category(Category.TOPS)
                .price(0)
                .build();
        Goods goods2 = Goods.builder()
                .brandName(brandName)
                .category(Category.OUTER)
                .price(0)
                .build();
        Goods goods3 = Goods.builder()
                .brandName(brandName)
                .category(Category.PANTS)
                .price(0)
                .build();
        Goods goods4 = Goods.builder()
                .brandName(brandName)
                .category(Category.SNEAKERS)
                .price(0)
                .build();
        Goods goods5 = Goods.builder()
                .brandName(brandName)
                .category(Category.BAG)
                .price(0)
                .build();
        Goods goods6 = Goods.builder()
                .brandName(brandName)
                .category(Category.HAT)
                .price(0)
                .build();
        Goods goods7 = Goods.builder()
                .brandName(brandName)
                .category(Category.SOCKS)
                .price(0)
                .build();
        Goods goods8 = Goods.builder()
                .brandName(brandName)
                .category(Category.ACCESSORY)
                .price(0)
                .build();

        return Arrays.asList(goods1, goods2, goods3, goods4, goods5, goods6, goods7, goods8);
    }

}