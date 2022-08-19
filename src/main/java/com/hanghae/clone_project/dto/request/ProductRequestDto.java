package com.hanghae.clone_project.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductRequestDto {
    private String title;
    private String price;
    private String content;
    private String category;
}
