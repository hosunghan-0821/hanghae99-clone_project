package com.hanghae.clone_project.dto.response;

import com.hanghae.clone_project.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProductsResponseDto {
    private Long productId;
    private String title;
    private String price;
    private String category;

    public ProductsResponseDto(Product product) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.category = product.getCategory();
    }
}
