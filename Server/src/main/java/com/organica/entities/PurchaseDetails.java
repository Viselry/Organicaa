package com.organica.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
@JsonIgnoreProperties({"purchase"})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "purchase")  // Loại bỏ purchase để tránh vòng đệ quy
public class PurchaseDetails {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @JsonBackReference
    @JsonIgnore
    private Purchase purchase;
}
