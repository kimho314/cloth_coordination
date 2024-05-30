package com.example.coordination.domain.entity;

import com.example.coordination.domain.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
@Entity
@Table(name = "BRAND")
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Category> categories = new ArrayList<>();

    public Integer getPriceSum() {
        return this.categories.stream()
                .mapToInt(Category::getPrice)
                .sum();
    }


    public Optional<Category> findCategory(CategoryType categoryType) {
        return this.categories.stream()
                .filter(it -> it.getCategoryType().equals(categoryType))
                .findFirst();
    }
}
