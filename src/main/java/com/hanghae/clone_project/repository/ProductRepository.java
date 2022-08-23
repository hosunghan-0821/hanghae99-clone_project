package com.hanghae.clone_project.repository;

import com.hanghae.clone_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryOrderByCreatedAtDesc(String category);
    List<Product> findByOrderByCreatedAtDesc();
    List<Product> findTop6ByOrderByIdAsc();
}
