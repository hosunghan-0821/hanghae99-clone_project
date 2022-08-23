package com.hanghae.clone_project.entity;


import com.hanghae.clone_project.dto.request.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Column(nullable = false)
    private String content;



    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    public Comment(CommentRequestDto commentRequestDto,User user,Product product) {
        this.content = commentRequestDto.getContent();
        this.user = user;
        this.product = product;
    }//같은자료형

    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();

    }

}
