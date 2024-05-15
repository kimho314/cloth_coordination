package com.example.coordination.domain.repository;

import com.example.coordination.domain.dto.GetBrandSumImpl;
import com.example.coordination.domain.dto.GetCategoriesMinPriceImpl;
import com.example.coordination.domain.dto.GetCategoryMinMaxPriceImpl;
import com.example.coordination.domain.entity.Goods;
import com.example.coordination.domain.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    void deleteAllByBrandName(String brandName);

    Optional<Goods> findByBrandNameAndCategory(String brandName, Category category);

    @Query(value = """
            SELECT category AS category,
                   brand_name AS brandName,
                   price AS price
            FROM (
                     SELECT category, brand_name, price,
                            ROW_NUMBER() OVER (PARTITION BY category ORDER BY price, BRAND_NAME desc) as rn
                     FROM goods
                 ) subquery
            WHERE rn = 1
            """, nativeQuery = true)
    List<GetCategoriesMinPriceImpl> getCategoryMinPrices();

    @Query(value = """
            SELECT brand_name AS brandName, sum(price) AS sumPrice
            FROM goods
            GROUP BY brand_name
            ORDER BY sumPrice
            LIMIT 1
            """
            , nativeQuery = true)
    Optional<GetBrandSumImpl> getBrandSums();

    @Query(value = """
            SELECT category AS catogry, brand_name AS brandName, price AS price
            FROM (
                     SELECT category, brand_name, price,
                            ROW_NUMBER() OVER (PARTITION BY category ORDER BY price ASC) as rn
                     FROM goods
                 ) subquery
            WHERE rn = 1 and CATEGORY = :category
            UNION
            SELECT category AS catogry, brand_name AS brandName, price AS price
            FROM (
                     SELECT category, brand_name, price,
                            ROW_NUMBER() OVER (PARTITION BY category ORDER BY price DESC) as rn
                     FROM goods
                 ) subquery
            WHERE rn = 1 and CATEGORY = :category
                """, nativeQuery = true)
    List<GetCategoryMinMaxPriceImpl> getCategoryMinMaxPrices(@Param("category") String category);

    List<Goods> findAllByBrandName(String brandName);
}
