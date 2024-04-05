package com.t3h.group3_petshop.model.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Integer quantity;
    private Double totalAmount;
    private ProductDTO product;
    private Long sizeId;
}
