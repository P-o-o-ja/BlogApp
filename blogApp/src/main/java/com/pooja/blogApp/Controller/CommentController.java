package com.pooja.blogApp.Controller;

import com.pooja.blogApp.payloads.ApiResponse;
import com.pooja.blogApp.payloads.CommentDto;
import com.pooja.blogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createdCommentDto=this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer commentId){
        CommentDto updatedCommentDto=this.commentService.updateComment(commentDto, commentId);
        return new ResponseEntity<CommentDto>(updatedCommentDto, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Succesfully", true), HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(){
        List<CommentDto> commentDtos=this.commentService.getAllComment();
        return new ResponseEntity<List<CommentDto>>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId){
        CommentDto commentDto=this.commentService.getCommentById(commentId);
        return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
    }
}
