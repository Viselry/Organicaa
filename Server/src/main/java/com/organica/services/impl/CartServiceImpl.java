package com.organica.services.impl;

import com.organica.entities.Cart;
import com.organica.entities.CartDetalis;
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

        CartDetalis cartDetalis = new CartDetalis();
        cartDetalis.setProducts(product);
        cartDetalis.setQuantity(quantity);
        cartDetalis.setAmount((int) (product.getPrice() * quantity));

        Cart cart = user.getCart();

        if (cart == null) {
            Cart newCart = new Cart();
            newCart.setUser(user);

            CartDetalis cartDetalis1 = new CartDetalis();
            cartDetalis1.setQuantity(quantity);
            cartDetalis1.setProducts(product);
            cartDetalis1.setAmount((int) (product.getPrice() * quantity));

            List<CartDetalis> pro = new java.util.ArrayList<>();
            pro.add(cartDetalis1);

            newCart.setCartDetalis(pro);
            newCart.setTotalAmount(cartDetalis1.getAmount());
            cartDetalis1.setCart(newCart);

            Cart savedCart = this.cartRepo.save(newCart);
            return this.modelMapper.map(savedCart, CartDto.class);
        }

        cartDetalis.setCart(cart);

        List<CartDetalis> list = cart.getCartDetalis();
        if (list == null) {
            list = new java.util.ArrayList<>();
        }

        AtomicReference<Boolean> flag = new AtomicReference<>(false);

        List<CartDetalis> updatedProducts = list.stream().map((i) -> {
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
            cartDetalis.setCart(cart);
            totalAmount.set(totalAmount.get() + (int) (quantity * product.getPrice()));
            list.add(cartDetalis);
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
        CartDetalis cartDetail = this.cartDetailsRepo.findByProductsAndCart(product, cart);
        int amount = cartDetail.getAmount();

        cart.setTotalAmount(cart.getTotalAmount() - amount);
        this.cartRepo.save(cart);

        this.cartDetailsRepo.delete(cartDetail);
    }

}
