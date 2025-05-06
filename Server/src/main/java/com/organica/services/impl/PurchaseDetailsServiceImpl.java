package com.organica.services.impl;

import com.organica.entities.Product;
import com.organica.entities.Purchase;
import com.organica.entities.PurchaseDetails;
import com.organica.repositories.ProductRepo;
import com.organica.repositories.PurchaseDetailsRepo;
import com.organica.repositories.PurchaseRepo;
import com.organica.services.PurchaseDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailsServiceImpl implements PurchaseDetailsService {

    private final PurchaseDetailsRepo purchaseDetailsRepository;

    @Autowired
    public PurchaseDetailsServiceImpl(PurchaseDetailsRepo purchaseDetailsRepository) {
        this.purchaseDetailsRepository = purchaseDetailsRepository;
    }

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private PurchaseRepo purchaseRepo;
    @Override
    public PurchaseDetails save(PurchaseDetails purchaseDetails) {
        Long productId = purchaseDetails.getProduct().getProductId();
        int purchaseId = purchaseDetails.getPurchase().getPurchase_id();
        System.out.println("product id:" + productId);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        Purchase purchase = purchaseRepo.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase not found: " + purchaseId));

        purchaseDetails.setProduct(product);
        purchaseDetails.setPurchase(purchase);

        return purchaseDetailsRepository.save(purchaseDetails);
    }
}
