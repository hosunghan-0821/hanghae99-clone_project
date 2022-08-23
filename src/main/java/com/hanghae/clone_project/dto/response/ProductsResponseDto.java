package com.hanghae.clone_project.dto.response;

import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ProductsResponseDto {
    private Long productId;
    private String title;
    private String price;
    private String category;
    private List<String> mainImageUrl;



    public static ProductsResponseDto of(Product product){

       return ProductsResponseDto.builder()
               .productId(product.getId())
               .title(product.getTitle())
               .mainImageUrl(product.getImageUrlList()
                       .stream()
                       .map(Image::getImageUrl)
                       .collect(Collectors.toList()))
               .build();
    }
}
