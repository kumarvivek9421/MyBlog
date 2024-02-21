package com.myblog.myblog.repository;

import com.myblog.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {


}
