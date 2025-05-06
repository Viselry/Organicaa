package com.organica.repositories;

import com.organica.entities.PurchaseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Long> {
}
