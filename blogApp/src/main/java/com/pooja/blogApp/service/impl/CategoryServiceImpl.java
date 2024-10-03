package com.pooja.blogApp.service.impl;

import com.pooja.blogApp.payloads.CategoryDto;
import com.pooja.blogApp.entity.Category;
import com.pooja.blogApp.exception.ResourceNotFoundException;
import com.pooja.blogApp.repository.CategoryRepository;
import com.pooja.blogApp.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(@Valid CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto, Category.class);
        Category createCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(createCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
       Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
       this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class)
                ;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos=categories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }
}
