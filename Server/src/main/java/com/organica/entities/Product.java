package com.organica.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProductId;
    @Column
    private String ProductName;

    private String Description;
    private Float Price;
    private String productBrand;
    private String imgLink;
    @ManyToMany
    @JoinTable(
            name = "product_category", // tên bảng phụ
            joinColumns = @JoinColumn(name = "product_id"), // khóa ngoại trỏ đến Product
            inverseJoinColumns = @JoinColumn(name = "category_id") // khóa ngoại trỏ đến Category
    )
    private List<Category> categoryList;
    @OneToMany(mappedBy = "products")
    private List<CartDetalis> list;

}
