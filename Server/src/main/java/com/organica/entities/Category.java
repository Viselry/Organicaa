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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;
    @ManyToMany(mappedBy = "categoryList")
    private List<Product> products;
}
