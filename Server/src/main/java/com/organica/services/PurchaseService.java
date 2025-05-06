package com.organica.services;

import com.organica.entities.Purchase;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {
    public Purchase save(Purchase purchase);
}
