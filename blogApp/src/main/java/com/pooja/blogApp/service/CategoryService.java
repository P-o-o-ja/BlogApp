package com.pooja.blogApp.service;

import com.pooja.blogApp.payloads.CategoryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(@Valid CategoryDto categoryDto);
    void deleteCategory(Integer categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    List<CategoryDto> getAllCategory();
    CategoryDto getCategoryById(Integer categoryId);
}
