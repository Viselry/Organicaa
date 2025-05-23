package com.organica.services.impl;

import com.organica.entities.Cart;
import com.organica.entities.CartDetails;
import com.organica.entities.Product;
import com.organica.entities.User;
import com.organica.payload.*;
import com.organica.repositories.CartDetailsRepo;
import com.organica.repositories.CartRepo;
import com.organica.repositories.ProductRepo;
import com.organica.repositories.UserRepo;
import com.organica.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartDetailsRepo cartDetailsRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto CreateCart(CartHelp cartHelp) {
        return null;
    }

    @Override
    public CartDto addProductToCart(CartHelp cartHelp) {

        long productId = cartHelp.getProductId();
        int quantity = cartHelp.getQuantity();
        String userEmail = cartHelp.getUserEmail();
        AtomicReference<Integer> totalAmount = new AtomicReference<>(0);

        User user = this.userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        CartDetails cartDetails = new CartDetails();
        cartDetails.setProducts(product);
        cartDetails.setQuantity(quantity);
        cartDetails.setAmount((int) (product.getPrice() * quantity));

        Cart cart = user.getCart();

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);

            CartDetails cartDetails1 = new CartDetails();
            cartDetails1.setQuantity(quantity);
            cartDetails1.setProducts(product);
            cartDetails1.setAmount((int) (product.getPrice() * quantity));

            List<CartDetails> pro = new java.util.ArrayList<>();
            pro.add(cartDetails1);

            newCart.setCartDetalis(pro);
            newCart.setTotalAmount(cartDetails1.getAmount());
            cartDetails1.setCart(newCart);

            Cart savedCart = this.cartRepo.save(newCart);
            return this.modelMapper.map(savedCart, CartDto.class);
        }

        cartDetails.setCart(cart);

        List<CartDetails> list = cart.getCartDetalis();
        if (list == null) {
            list = new java.util.ArrayList<>();
        }

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        List<CartDetails> updatedProducts = list.stream().map((i) -> {
            if (i.getProducts().getProductId() == productId) {
                i.setQuantity(quantity);
                i.setAmount((int) (i.getQuantity() * product.getPrice()));
                flag.set(true);
            }
            totalAmount.set(totalAmount.get() + i.getAmount());
            return i;
        }).collect(Collectors.toList());

        if (flag.get()) {
            list.clear();
            list.addAll(updatedProducts);
        } else {
            cartDetails.setCart(cart);
            totalAmount.set(totalAmount.get() + (int) (quantity * product.getPrice()));
            list.add(cartDetails);
        }

        cart.setCartDetalis(list);
        cart.setTotalAmount(totalAmount.get());

        Cart savedCart = this.cartRepo.save(cart);
        return this.modelMapper.map(savedCart, CartDto.class);
    }

    @Override
    public CartDto GetCart(String userEmail) {
        User user = this.userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Cart cart = this.cartRepo.findByUser(user);
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void RemoveById(Long productId, String userEmail) {
        User user = this.userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Cart cart = this.cartRepo.findByUser(user);
        CartDetails cartDetail = this.cartDetailsRepo.findByProductsAndCart(product, cart);
        int amount = cartDetail.getAmount();

        cart.setTotalAmount(cart.getTotalAmount() - amount);
        this.cartRepo.save(cart);

        this.cartDetailsRepo.delete(cartDetail);
    }

}
