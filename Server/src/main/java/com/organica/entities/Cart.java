package com.organica.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    private float totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartDetails> cartDetalis = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartDetails> getCartDetalis() {
        return cartDetalis;
    }

    public void setCartDetalis(List<CartDetails> cartDetalis) {
        this.cartDetalis = cartDetalis;
        for (CartDetails detail : cartDetalis) {
            detail.setCart(this); // đảm bảo ánh xạ 2 chiều đúng
        }
    }
}

