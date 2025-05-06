package com.organica.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.organica.payload.PagedResponseDTO;
import com.organica.payload.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    //create
    ProductDto CreateProduct(ProductDto productDto) throws JsonProcessingException;

    //read
    ProductDto ReadProduct(Long ProductId);


    //readAll
    PagedResponseDTO<ProductDto> getAllProductsPaged(int page, int size);


    //delete
    void DeleteProduct(Long productId);


    //update
    ProductDto UpdateProduct(ProductDto productDto,Long ProductId);



}

