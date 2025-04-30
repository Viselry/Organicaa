package com.organica.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.organica.payload.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto CreateProduct(ProductDto productDto) throws JsonProcessingException;

    //read
    ProductDto ReadProduct(Integer ProductId);


    //readAll
    List<ProductDto> ReadAllProduct();


    //delete
    void DeleteProduct(Integer productId);


    //update
    ProductDto UpdateProduct(ProductDto productDto,Integer ProductId);



}

