package com.hanghae.clone_project.dto.response.productDatail;

import com.hanghae.clone_project.entity.ProductDetail;
import lombok.Getter;

@Getter
public class ProductDetailImageResponseDto {
    private String detailImage_Url;

    public ProductDetailImageResponseDto(ProductDetail productDetail){
        //(오류)this.image_Url = productDetail;
        this.detailImage_Url = productDetail.getDetailImage_Url();
    }

}
