package com.hanghae.clone_project.controller;

import com.hanghae.clone_project.dto.response.productDatail.ApiResponseMessage;
import com.hanghae.clone_project.dto.response.productDatail.ResponseDto;
import com.hanghae.clone_project.dto.response.productDatail.ProductDetailResponseDto;
import com.hanghae.clone_project.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @GetMapping("api/v1/productDetails/{productId}")
    public ResponseDto<ProductDetailResponseDto> getProductDetail(@PathVariable Long productId){
        return productDetailService.getProductDetail(productId);

    }

    @PostMapping("api/v1/productDetails/{productId}")
    public ResponseEntity<ApiResponseMessage> createProductDetail(@PathVariable Long productId,
             @RequestPart(required = false) List<MultipartFile> multipartFile) throws IOException {
        return productDetailService.createDetail(productId, multipartFile);
    }

    @DeleteMapping("api/v1/productDetails/{productId}")
    public ResponseEntity deleteProductDetail(@PathVariable Long productId){
        return productDetailService.deleteDetail(productId);
    }

    @PutMapping("api/v1/productDetails/{productId}")
    public ResponseEntity putProductDetail(@PathVariable Long productId,
                        @RequestPart(required = false) List<MultipartFile> multipartFile) {
        return productDetailService.updateDetail(productId, multipartFile);
    }

}
