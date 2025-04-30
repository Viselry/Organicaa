package com.organica.controllers;

import com.organica.entities.Purchase;
import com.organica.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/")
    public ResponseEntity<Purchase> purchase(@RequestBody Purchase purchase) {
        this.purchaseService.save(purchase);
        return new ResponseEntity<>(purchase, HttpStatusCode.valueOf(200));
    }
}
