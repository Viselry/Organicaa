package com.organica.services.impl;

import com.organica.entities.Purchase;
import com.organica.repositories.PurchaseRepo;
import com.organica.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Override
    public void save(Purchase purchase) {
        purchaseRepo.save(purchase);
    }
}
