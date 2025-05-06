package com.organica.services.impl;

import com.organica.entities.Purchase;
import com.organica.entities.User;
import com.organica.repositories.PurchaseRepo;
import com.organica.repositories.UserRepo;
import com.organica.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public Purchase save(Purchase purchase) {
        System.out.println("purchase" + purchase);
        int userId = purchase.getUser().getUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        purchase.setUser(user); // attach managed entity
        return purchaseRepo.save(purchase);
    }

}
