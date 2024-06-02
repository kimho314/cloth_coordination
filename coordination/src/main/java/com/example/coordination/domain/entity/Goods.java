package com.example.coordination.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "GOODS")
@NoArgsConstructor
@AllArgsConstructor
public class Goods extends BaseEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "BRAND_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column(name = "AMOUNT")
    private Integer amount;

}