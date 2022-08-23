package com.hanghae.clone_project.repository;

import com.hanghae.clone_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{


}
