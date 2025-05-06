package com.organica.payload;

import lombok.Data;

@Data
public class PurchaseDetailsDTO {
    private Long productId;
    private int quantity;
}
