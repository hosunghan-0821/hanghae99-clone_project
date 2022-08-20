package com.hanghae.clone_project.entity;

import com.hanghae.clone_project.dto.request.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category; //문방 /리빙

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade =CascadeType.REMOVE)
    @Column(nullable = true)
    private List<Image> imageUrlList = new ArrayList<>();

    public void update(ProductRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }
}
