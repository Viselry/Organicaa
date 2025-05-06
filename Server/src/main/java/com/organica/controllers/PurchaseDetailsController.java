package com.organica.controllers;

import com.organica.entities.PurchaseDetails;
import com.organica.services.PurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase-details")
public class PurchaseDetailsController {

    private final PurchaseDetailsService purchaseDetailsService;

    @Autowired
    public PurchaseDetailsController(PurchaseDetailsService purchaseDetailsService) {
        this.purchaseDetailsService = purchaseDetailsService;
    }

    @PostMapping
    public PurchaseDetails savePurchaseDetails(@RequestBody PurchaseDetails purchaseDetails) {
        return purchaseDetailsService.save(purchaseDetails);

    }
}
