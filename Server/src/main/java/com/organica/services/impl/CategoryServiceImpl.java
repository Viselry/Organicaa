package com.organica.services.impl;

import com.organica.entities.Category;
import com.organica.payload.CategoryDto;
import com.organica.repositories.CategoryRepo;
import com.organica.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow();
        category.setCategoryName(categoryDto.getCategoryName());
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow();
        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow();
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(cat -> modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
