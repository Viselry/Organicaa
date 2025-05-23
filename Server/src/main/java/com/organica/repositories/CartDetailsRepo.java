package com.organica.repositories;

import com.organica.entities.Cart;
import com.organica.entities.CartDetails;
import com.organica.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailsRepo extends JpaRepository<CartDetails,Integer> {
    public void deleteByProductsAndCart(Product product, Cart cart);
    public CartDetails findByProductsAndCart(Product product, Cart cart);
}
