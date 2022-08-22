package com.hanghae.clone_project.dto.response.productDatail;

import com.hanghae.clone_project.dto.response.ImagesResponseDto;
import com.hanghae.clone_project.entity.Image;
import com.hanghae.clone_project.entity.Product;
import com.hanghae.clone_project.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductDetailResponseDto {

    private Long productId;
    private String title;
    private String price;
    private String content;
    private String category;
    private List image_Url;
    //(Json형식으로 출력됨)private List<ProductDetailImageResponseDto> detailImage_Url;
    //(Json형식으로 출력됨)private List detailImage_Url;
    private List detailImage_Url;

    //타입을 맞추려면 생성자를 별도로 생성해줘야 함.
    //엔티티에서 값을 꺼네요는 거니까 request와는 다르게 해주어야 함.

}
