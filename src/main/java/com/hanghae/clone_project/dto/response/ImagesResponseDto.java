package com.hanghae.clone_project.dto.response;

import com.hanghae.clone_project.entity.Image;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ImagesResponseDto {
    private String imageUrl;

    public ImagesResponseDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }

}
