package com.organica.repositories;

import com.organica.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {

}
