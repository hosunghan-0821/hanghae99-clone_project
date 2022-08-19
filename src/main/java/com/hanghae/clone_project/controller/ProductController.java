package com.hanghae.clone_project.controller;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto requestDto){
        return productService.createProduct(requestDto);
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> getAllProduct(){
        return productService.getAllProduct();
    }

    // 상품 상세 조회

    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequestDto requestDto){
        return productService.updateProduct(productId, requestDto);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }

}
