package com.example.coordination.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRICE")
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseEntity {
    @Id
    @Column(name = "ID", nullable = false)
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