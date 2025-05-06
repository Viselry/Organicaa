package com.organica.controllers;

import com.organica.payload.ApiResponse;
import com.organica.payload.CategoryDto;
import com.organica.payload.ProductDto;
import com.organica.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Create Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Update Category
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete Category
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted successfully"), HttpStatus.OK);
    }

    // Get Single Category
    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    // Get All Categories
    @GetMapping("/get")
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(required = false) Integer limit) {
        List<CategoryDto> categories;
        if (limit != null) {
            categories = categoryService.getCategoryWithLimit(limit);
        }
        else {
             categories = categoryService.getAllCategories();
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/get/products/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(required = false) Integer limit) {

        List<ProductDto> productDtos;
        if (limit != null) {
            productDtos = categoryService.getProductsByCategoryIdWithLimit(categoryId, limit);
        } else {
            productDtos = categoryService.getProductsByCategoryId(categoryId);
        }

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

}
