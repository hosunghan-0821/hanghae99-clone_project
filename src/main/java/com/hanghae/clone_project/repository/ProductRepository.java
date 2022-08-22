package com.hanghae.clone_project.repository;

import com.hanghae.clone_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderByCreatedAtDesc();
<<<<<<< HEAD

    //List<Product> findById(Long productId);
=======
    List<Product> findTop6ByOrderByIdAsc();
   // List<Product> findAllByIdAsc();
>>>>>>> origin/feature/signup
}
