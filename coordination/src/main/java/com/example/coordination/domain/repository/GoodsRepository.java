package com.example.coordination.domain.repository;

import com.example.coordination.domain.dto.MinBrandDto;
import com.example.coordination.domain.dto.MinCategoryDto;
import com.example.coordination.domain.dto.MinMaxCategoryDto;
import com.example.coordination.domain.entity.Brand;
import com.example.coordination.domain.entity.Category;
import com.example.coordination.domain.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinCategoryDto(g.category.id, MIN(g.amount)) FROM Goods g GROUP BY g.category
            """)
    List<MinCategoryDto> findMinCategoryPrices();

    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinBrandDto(g.brand.id, SUM(g.amount)) FROM Goods g GROUP BY g.brand.id ORDER BY SUM(g.amount) LIMIT 1
            """)
    Optional<MinBrandDto> findFirstMinBrandPrice();

    Optional<Goods> findFirstByCategory_IdAndAmountOrderByIdDesc(Long categoryId, Integer amount);

    List<Goods> findAllByBrand_Id(Long brandId);

    Optional<Goods> findByCategoryAndBrand(Category category, Brand brand);

    @Query(value = """
                SELECT new com.example.coordination.domain.dto.MinMaxCategoryDto(g.category.id, MIN(g.amount), MAX(g.amount)) FROM Goods g WHERE g.category.id = :categoryId GROUP BY g.category.id
            """)
    Optional<MinMaxCategoryDto> findMinMaxCategoryByCategoryId(@Param("categoryId") Long categoryId);
}
