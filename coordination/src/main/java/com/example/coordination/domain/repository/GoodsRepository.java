package com.example.coordination.domain.repository;

import com.example.coordination.domain.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<Goods, String> {
}
