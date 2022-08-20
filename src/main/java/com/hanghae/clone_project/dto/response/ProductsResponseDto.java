package com.hanghae.clone_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ProductsResponseDto {
    private Long productId;
    private String title;
    private String price;
    private String category;
    private List<ImagesResponseDto> imageUrl = new ArrayList<>();

//    public ProductsResponseDto(Product product) {
//        this.productId = product.getId();
//        this.title = product.getTitle();
//        this.price = product.getPrice();
//        this.category = product.getCategory();
//    }
}
