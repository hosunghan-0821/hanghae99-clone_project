package com.hanghae.clone_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductResponseDto {
    private Long productId;
    private String title;
    private String price;
    private String content;
    private String category;
    private List<ImagesResponseDto> imageUrl = new ArrayList<>();

//    public ProductResponseDto(Product product) {
//        this.productId = product.getId();
//        this.title = product.getTitle();
//        this.price = product.getPrice();
//        this.content = product.getContent();
//        this.category = product.getCategory();
//    }
}
