package com.pooja.blogApp.service.impl;

import com.pooja.blogApp.entity.Category;
import com.pooja.blogApp.entity.Post;
import com.pooja.blogApp.entity.User;
import com.pooja.blogApp.exception.ResourceNotFoundException;
import com.pooja.blogApp.payloads.PostDto;
import com.pooja.blogApp.payloads.PostResponse;
import com.pooja.blogApp.repository.CategoryRepository;
import com.pooja.blogApp.repository.PostRepository;
import com.pooja.blogApp.repository.UserRepository;
import com.pooja.blogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        Post post=this.modelMapper.map(postDto, Post.class);
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id" ,userId));
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        post.setPostImageName("default.png");
        post.setPostDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost=this.postRepository.save(post);
        PostDto newPostDto=this.modelMapper.map(newPost, PostDto.class);
        return newPostDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));

        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getPostContent());
        post.setPostImageName(postDto.getPostImageName());
        Post updatedPost=this.postRepository.save(post);
        PostDto updatedPostDto=this.modelMapper.map(updatedPost, PostDto.class);
        return updatedPostDto;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable p= PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost=this.postRepository.findAll(p);
        List<Post> pageContent=pagePost.getContent();
        List<PostDto> postDtos=pageContent.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id", postId));
        PostDto postDto=this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        List<Post> posts=this.postRepository.findByCategory(category);
        List<PostDto>postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
        List<Post> posts=this.postRepository.findByUser(user);
        List<PostDto>postDtos=posts.stream().map((post) ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> searchedPosts=this.postRepository.findByPostTitleContaining(keyword);
        List<PostDto> searchedPostDto=searchedPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return searchedPostDto;
    }
}
