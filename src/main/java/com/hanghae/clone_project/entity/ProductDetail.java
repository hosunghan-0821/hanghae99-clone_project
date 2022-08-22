package com.hanghae.clone_project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetail {

    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String detailImage_Url;


    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    //detailImage_Url String -> ProductDetail 변환용
    public ProductDetail(String detailImage_Url) {
        this.detailImage_Url = detailImage_Url;
    }

    //public update()
}
