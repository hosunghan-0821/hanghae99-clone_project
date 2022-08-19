package com.hanghae.clone_project.service;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import com.hanghae.clone_project.dto.response.ProductsResponseDto;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ResponseEntity<?> createProduct(ProductRequestDto requestDto) {

        Product product = Product.builder()
                .title(requestDto.getTitle())
                .price(requestDto.getPrice())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .build();

        productRepository.save(product);

        return new ResponseEntity<>("상품이 등록되었습니다.", HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> getAllProduct() {

        List<Product> products = productRepository.findByOrderByCreatedAtDesc();
        List<ProductsResponseDto> productList = new ArrayList<>();

        for (Product product : products){
            ProductsResponseDto productsResponseDto = new ProductsResponseDto(product);
            productList.add(productsResponseDto);
        }

        return productList;
    }

    //상품 상세조회

    @Transactional
    public ResponseEntity<?> updateProduct(Long productId, ProductRequestDto requestDto) {

        if (!productRepository.findById(productId).isPresent()){
            return new ResponseEntity<>("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(productId).get();
        product.update(requestDto);

        return new ResponseEntity<>("상품이 수정되었습니다.", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Long productId) {

        if (!productRepository.findById(productId).isPresent()){
            return new ResponseEntity<>("해당 상품이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        Product product = productRepository.findById(productId).get();
        productRepository.delete(product);

        return new ResponseEntity<>("상품이 삭제되었습니다.", HttpStatus.OK);
    }
}
