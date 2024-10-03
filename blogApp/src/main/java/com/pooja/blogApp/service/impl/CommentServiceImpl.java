package com.pooja.blogApp.service.impl;

import com.pooja.blogApp.payloads.CommentDto;
import com.pooja.blogApp.entity.Comment;
import com.pooja.blogApp.entity.Post;
import com.pooja.blogApp.exception.ResourceNotFoundException;
import com.pooja.blogApp.payloads.CommentDto;
import com.pooja.blogApp.repository.CommentRepository;
import com.pooja.blogApp.repository.PostRepository;
import com.pooja.blogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id", postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment createdComment=this.commentRepository.save(comment);
        CommentDto createdCommentDto=this.modelMapper.map(createdComment, CommentDto.class);
        return createdCommentDto;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {

       Comment comment=this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
       comment.setComment(commentDto.getComment());
       Comment updateComment=this.commentRepository.save(comment);
       CommentDto updatedCommentDto=this.modelMapper.map(updateComment, CommentDto.class);
       return updatedCommentDto;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
        this.commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getAllComment() {
        List<Comment> comments=this.commentRepository.findAll();
        List<CommentDto> commentDtos=comments.stream().map((comment)->this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentById(Integer commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
        CommentDto commentDto=this.modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
