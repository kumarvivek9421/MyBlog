package com.myblog.myblog.service.impl;

import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.entity.Post;
import com.myblog.myblog.exception.BlogAPIException;
import com.myblog.myblog.exception.ResourceNotFoundException;
import com.myblog.myblog.payload.CommentDto;
import com.myblog.myblog.repository.CommentRepository;
import com.myblog.myblog.repository.PostRepository;
import com.myblog.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
// retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
// set post to comment entity
        comment.setPost(post);

//        comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if (!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", id));

        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!Objects.equals(comment.getPost().getId(), post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepository.deleteById(comment.getId());
    }

    CommentDto mapToDTO(Comment newComment) {
        CommentDto dto= new CommentDto();
        dto.setId(newComment.getId());
        dto.setName(newComment.getName());
        dto.setEmail(newComment.getEmail());
        dto.setBody(newComment.getBody());
        return dto;
    }

    Comment mapToEntity(CommentDto commentDto) {
        Comment comment= new Comment();
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());
            return comment;
    }
}
