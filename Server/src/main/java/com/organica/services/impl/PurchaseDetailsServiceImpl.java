package com.organica.services.impl;

import com.organica.entities.PurchaseDetails;
import com.organica.repositories.PurchaseDetailsRepo;
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

    @Override
    public PurchaseDetails save(PurchaseDetails purchaseDetails) {
        return purchaseDetailsRepository.save(purchaseDetails);
    }
}
