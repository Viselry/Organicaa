package com.organica.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.organica.entities.Product;
import com.organica.payload.ProductDto;
import com.organica.produce.KafkaProducerService;
import com.organica.repositories.ProductRepo;
import com.organica.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    // Create
    @CachePut(value = "PRODUCT_CACHE", key = "#result.productId")
    public ProductDto CreateProduct(ProductDto productDto) throws JsonProcessingException {
        Product product = this.modelMapper.map(productDto, Product.class);

        // Không còn compress img nữa
        Product savedProduct = this.productRepo.save(product);

        ProductDto savedProductDto = this.modelMapper.map(savedProduct, ProductDto.class);

        // Create message content with product info and current timestamp
        LocalDateTime currentTime = LocalDateTime.now();
        String messageContent = String.format("{\"product\":%s,\"timestamp\":\"%s\"}",
                new ObjectMapper().writeValueAsString(savedProductDto),
                currentTime);

        // Send message to Kafka topic "new_product"
        kafkaProducerService.send("new_product", messageContent);

        return savedProductDto;
    }
    // Read One
    @Override
    @Cacheable(value = "PRODUCT_CACHE", key = "#productId")
    public ProductDto ReadProduct(Integer productId) {
        Product product = this.productRepo.findById(productId).orElseThrow();
        return this.modelMapper.map(product, ProductDto.class);
    }

    // Read All
    @Override
    @Cacheable(value = "PRODUCT_CACHE", key = "'all'")
    public List<ProductDto> ReadAllProduct() {
        List<Product> allProducts = this.productRepo.findAll();
        return allProducts.stream()
                .map(product -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    // Delete
    @Override
    @CacheEvict(value = "PRODUCT_CACHE", key = "#productId")
    public void DeleteProduct(Integer productId) {
        this.productRepo.deleteById(productId);
    }

    // Update
    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#result.productId")
    public ProductDto UpdateProduct(ProductDto productDto, Integer productId) {
        Product existingProduct = this.productRepo.findById(productId).orElseThrow();

        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImgLink(productDto.getImg()); // lưu link ảnh thay vì img byte

        Product updatedProduct = this.productRepo.save(existingProduct);

        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

}
