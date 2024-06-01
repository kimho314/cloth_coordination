package com.example.coordination.domain.repository;

import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryType(CategoryType categoryType);
}
