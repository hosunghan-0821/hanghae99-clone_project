package com.hanghae.clone_project.dto.response;

import com.hanghae.clone_project.entity.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ImagesResponseDto {
    private String imageUrl;

    public ImagesResponseDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }
}
