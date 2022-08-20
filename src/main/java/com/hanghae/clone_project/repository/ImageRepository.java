package com.hanghae.clone_project.repository;

import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByProduct(Product product);
}
