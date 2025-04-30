package com.organica.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchase_id;
    private Date purchase_date;
    @ManyToOne()
    private Product product;
    @ManyToOne
    private User user;
    private int quantity;
}
