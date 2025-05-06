package com.organica.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.organica.entities.Product;
import com.organica.payload.PagedResponseDTO;
import com.organica.payload.ProductDto;
import com.organica.produce.KafkaProducerService;
import com.organica.repositories.ProductRepo;
import com.organica.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public ProductDto ReadProduct(Long productId) {
        Product product = this.productRepo.findById(productId).orElseThrow();
        return this.modelMapper.map(product, ProductDto.class);
    }

    // Read All
    @Override
    @Cacheable(value = "PRODUCT_CACHE", key = "'page_' + #page + '_size_' + #size")
    public PagedResponseDTO<ProductDto> getAllProductsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());
        Page<Product> productPage = productRepo.findAll(pageable);

        List<ProductDto> dtoList = productPage.getContent().stream()
                .map(p -> modelMapper.map(p, ProductDto.class))
                .toList();

        Page<ProductDto> dtoPage = new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
        return new PagedResponseDTO<>(dtoPage); // an toàn vì đã chuẩn hóa bằng constructor như trên
    }



    // Delete
    @Override
    @CacheEvict(value = "PRODUCT_CACHE", key = "#productId")
    public void DeleteProduct(Long productId) {
        this.productRepo.deleteById(productId);
    }

    // Update
    @Override
    @CachePut(value = "PRODUCT_CACHE", key = "#result.productId")
    public ProductDto UpdateProduct(ProductDto productDto, Long ProductId) {
        Product existingProduct = this.productRepo.findById(ProductId).orElseThrow();

        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImgLink(productDto.getImg()); // lưu link ảnh thay vì img byte

        Product updatedProduct = this.productRepo.save(existingProduct);

        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

}
