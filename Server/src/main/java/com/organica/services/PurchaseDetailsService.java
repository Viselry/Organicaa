package com.organica.services;

import com.organica.entities.PurchaseDetails;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseDetailsService {
    PurchaseDetails save(PurchaseDetails purchaseDetails);
}
