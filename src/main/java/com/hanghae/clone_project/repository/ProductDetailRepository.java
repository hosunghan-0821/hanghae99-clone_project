package com.hanghae.clone_project.repository;

import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAllByProduct(Product product);
    List<ProductDetail> findAllByProductId(Long productId);
    Optional<ProductDetail> findByProductId(Long productId);
}
