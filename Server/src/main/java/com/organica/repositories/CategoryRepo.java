package com.organica.repositories;

import com.organica.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryName(String categoryName);

    Optional<Category> findById(Long categoryId);
}
