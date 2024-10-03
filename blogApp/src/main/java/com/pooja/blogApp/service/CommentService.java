package com.pooja.blogApp.service;

import com.pooja.blogApp.payloads.CommentDto;
import com.pooja.blogApp.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);
    CommentDto updateComment(CommentDto commentDto, Integer commentId);
    void deleteComment(Integer commentId);
    List<CommentDto> getAllComment();
    CommentDto getCommentById(Integer commentId);
}
