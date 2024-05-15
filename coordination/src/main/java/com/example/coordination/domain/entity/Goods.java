package com.example.coordination.domain.entity;

import com.example.coordination.domain.enums.Category;
import jakarta.persistence.*;
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


}