package com.organica.services;

import com.organica.entities.Purchase;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {
    public void save(Purchase purchase);
}
