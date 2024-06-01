package com.example.coordination.domain.entity;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 100, name = "CATEGORY_TYPE")
    private CategoryType categoryType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Price> prices = new ArrayList<>();

}
