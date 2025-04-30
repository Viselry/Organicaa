package com.organica.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.organica.entities.Category;
import com.organica.payload.ApiResponse;
import com.organica.payload.ProductDto;
import com.organica.services.CategoryService;
import com.organica.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductControllers {

    private final ProductService productService;
    private final CategoryService categoryService; // Thêm service để lấy Category từ id

    // Create Product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ProductDto> CreateProduct(@RequestBody ProductDto productDto) throws JsonProcessingException {
        // Nếu cần xử lý thêm categoryList thì xử lý trong ProductService
        ProductDto savedProduct = this.productService.CreateProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    // Get by Id
    @GetMapping("/{productid}")
    public ResponseEntity<ProductDto> GetById(@PathVariable Integer productid) {
        ProductDto product = this.productService.ReadProduct(productid);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Get All Products
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = this.productService.ReadAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Delete Product
    @DeleteMapping("/del/{ProductId}")
    public ResponseEntity<ApiResponse> Delete(@PathVariable Integer ProductId) {
        this.productService.DeleteProduct(ProductId);
        return new ResponseEntity<>(new ApiResponse("Product deleted"), HttpStatus.OK);
    }

    // Update Product
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{ProductId}")
    public ResponseEntity<ProductDto> UpdateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Integer ProductId) {
        ProductDto updatedProduct = this.productService.UpdateProduct(productDto, ProductId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}
