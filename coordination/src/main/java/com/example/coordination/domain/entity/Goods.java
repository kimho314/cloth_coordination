package com.example.coordination.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "GOODS")
@NoArgsConstructor
@AllArgsConstructor
public class Goods extends BaseEntity {
    @Id
    @Column(name = "BRAND_NAME", nullable = false, length = 1000)
    private String brandName;

    @Column(name = "TOP")
    private Integer top;

    @Column(name = "OUTER")
    private Integer outer;

    @Column(name = "PANTS")
    private Integer pants;

    @Column(name = "SNEAKERS")
    private Integer sneakers;

    @Column(name = "BAG")
    private Integer bag;

    @Column(name = "HAT")
    private Integer hat;

    @Column(name = "SOCKS")
    private Integer socks;

    @Column(name = "ACCESSORY")
    private Integer accessory;

}