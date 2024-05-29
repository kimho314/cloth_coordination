package com.example.coordination.domain.entity;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "CATEGORY_TYPE")
    private CategoryType categoryType;

    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ManyToOne
    private Brand brand;
}
