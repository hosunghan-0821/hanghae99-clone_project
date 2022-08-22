package com.hanghae.clone_project.dto.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDetailRequestDto {

    private String detailImage_Url;

}
