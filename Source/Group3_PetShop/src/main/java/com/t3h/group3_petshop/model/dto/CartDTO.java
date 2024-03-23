package com.t3h.group3_petshop.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartDTO {
    private Long id;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modifiedDate;

    private String modifiedBy;
    private Long userId;

    private Long productId;

    private Long sizeId;

    private Double total;

    private String productName;

    private String userName;

    private String size;
}
