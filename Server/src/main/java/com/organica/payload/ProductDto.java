package com.organica.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ProductDto {
    private long productId;
    private String ProductName;
    private String brandName;
    private String Description;
    private Float Price;
    private Float Weight;
    private String Img;
    private ArrayList<Integer> categoryIds;
}
