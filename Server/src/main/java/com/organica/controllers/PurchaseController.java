package com.organica.controllers;

import com.organica.entities.Product;
import com.organica.entities.Purchase;
import com.organica.entities.PurchaseDetails;
import com.organica.entities.User;
import com.organica.payload.PurchaseDetailsDTO;
import com.organica.repositories.ProductRepo;
import com.organica.repositories.PurchaseRepo;
import com.organica.repositories.UserRepo;
import com.organica.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @PostMapping("/")
    public ResponseEntity<?> createPurchase(@RequestBody com.organica.dto.PurchaseDTO purchaseDTO) {
        System.out.println(purchaseDTO);
        Purchase purchase = new Purchase();
        purchase.setPurchase_date(purchaseDTO.getPurchase_date());
        // Lấy user từ userId
        User user = userRepo.findById(purchaseDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        purchase.setUser(user);

        List<PurchaseDetails> detailsList = new ArrayList<>();
        for (PurchaseDetailsDTO detailDTO : purchaseDTO.getPurchaseDetails()) {
            PurchaseDetails detail = new PurchaseDetails();

            Product product = productRepo.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setPurchase(purchase); // Gán ngược để tránh lỗi

            detailsList.add(detail);
        }

        purchase.setPurchaseDetails(detailsList);
        Purchase saved = purchaseRepo.save(purchase);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("purchaseId", saved.getPurchase_id()));
    }
}
