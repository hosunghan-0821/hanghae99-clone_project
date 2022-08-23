package com.hanghae.clone_project.controller;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ProductResponseDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.security.UserDetailsImpl;
import com.hanghae.clone_project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                           @RequestPart List<MultipartFile> mainMultipartFile,
                                           @RequestPart List<MultipartFile> detailMultipartFile,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        return productService.createProduct(requestDto, mainMultipartFile, detailMultipartFile, userDetails);
    }

    //상품 전체조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductsResponseDto>> readAllProduct(){

        List<ProductsResponseDto> responseDtos = productService.readAllProduct();

        return ResponseEntity.ok()
                .body(responseDtos);
    }

    //상품 상세조회
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> readProduct(@PathVariable Long productId){

        ProductResponseDto responseDto = productService.readProduct(productId);

        return ResponseEntity.ok()
                .body(responseDto);
    }

    //상품 수정
    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                           @RequestBody ProductRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){

        return productService.updateProduct(productId, requestDto, userDetails);
    }

    //상품 삭제
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){

        return productService.deleteProduct(productId);
    }


    @GetMapping("/products/mainitems")
    public ResponseEntity<?> getMainItems(){
        return productService.getMainItems();
    }

}
