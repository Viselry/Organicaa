package com.organica.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties({"purchaseDetails"})
@Entity
@NoArgsConstructor
@Data
@ToString(exclude = "purchaseDetails")  // Loại bỏ purchaseDetails để tránh vòng đệ quy
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchase_id;
    private Date purchase_date;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<PurchaseDetails> purchaseDetails = new ArrayList<>();
}
