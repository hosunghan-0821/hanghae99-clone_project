package com.hanghae.clone_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<String> mainImageUrl;
    private List<String> detailImageUrl;

}
