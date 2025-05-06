package com.organica.dto;

import com.organica.payload.PurchaseDetailsDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PurchaseDTO {
    private Date purchase_date;
    private int userId;
    private List<PurchaseDetailsDTO> purchaseDetails;
}
