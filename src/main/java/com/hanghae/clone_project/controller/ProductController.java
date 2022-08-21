package com.hanghae.clone_project.controller;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ProductResponseDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    //상품 등록
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestPart ProductRequestDto requestDto,
                                           @RequestPart(required = false) List<MultipartFile> multipartFile){
        return productService.createProduct(requestDto, multipartFile);
    }

    //상품 전체조회
    @GetMapping("/products")
    public List<ProductsResponseDto> getAllProduct(){
        return productService.getAllProduct();
    }

    //상품 상세조회
    @GetMapping("/products/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    //상품 수정
    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequestDto requestDto){
        return productService.updateProduct(productId, requestDto);
    }

    //상품 삭제
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }

}
