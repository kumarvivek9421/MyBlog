package com.myblog.myblog.controller;

import com.myblog.myblog.payload.PostDto;
import com.myblog.myblog.service.PostService;
import com.myblog.myblog.service.impl.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

//    create post
//    http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        PostDto dto= postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
//    getAll post
//    http://localhost:8080/api/posts?pageNo=0&pageSize=10&sortBy=title&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
//    getSingle post
//    http://localhost:8080/api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return new ResponseEntity<> (postService.getPostById(id), HttpStatus.OK);
    }
//    update post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("id") long id
            ){
        PostDto postResponse= postService.updatePost(postDto, id);

        return ResponseEntity.ok(postResponse);
    }
//    delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(
            @PathVariable("id") long id
    ){
        postService.deleteById(id);
        return new ResponseEntity<String>("Post is delete!!", HttpStatus.OK);
    }
}

//29-03-2023-after 19:00 continue video