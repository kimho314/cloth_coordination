package com.example.coordination.domain.repository;

import com.example.coordination.domain.dto.MinBrandDto;
import com.example.coordination.domain.dto.MinCategoryDto;
import com.example.coordination.domain.dto.MinMaxCategoryDto;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinCategoryDto(p.category.id, MIN(p.amount)) FROM Price p GROUP BY p.category
            """)
    List<MinCategoryDto> findMinCategoryPrices();

    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinBrandDto(p.brand.id, SUM(p.amount)) FROM Price p GROUP BY p.brand.id ORDER BY SUM(p.amount) LIMIT 1
            """)
    Optional<MinBrandDto> findFirstMinBrandPrice();

    Optional<Price> findFirstByCategory_IdAndAmountOrderByIdDesc(Long categoryId, Integer amount);

    List<Price> findAllByBrand_Id(Long brandId);

    Optional<Price> findByCategoryAndBrand(Category category, Brand brand);

    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinMaxCategoryDto(p.category.id, MIN(p.amount), MAX(p.amount)) FROM Price p WHERE p.category.id = :categoryId GROUP BY p.category.id
            """)
    Optional<MinMaxCategoryDto> findMinMaxCategoryByCategoryId(@Param("categoryId") Long categoryId);
}
