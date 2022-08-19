package com.hanghae.clone_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Product {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String category; //문방 /리빙

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade =CascadeType.REMOVE)
    private List<Image> imageList = new ArrayList<>();

}
