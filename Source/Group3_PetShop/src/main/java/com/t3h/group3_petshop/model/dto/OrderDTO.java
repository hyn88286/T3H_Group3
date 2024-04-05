package com.t3h.group3_petshop.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;

    private LocalDateTime createdDate;

    private String createdBy;

    private LocalDateTime modifiedDate;

    private String modifiedBy;
    private String username;
    private Long userId;
    private Long sizeId;


    private Double totalAmount;
    // Thêm danh sách các mục sản phẩm trong đơn hàng
    private List<OrderDetailDTO> orderDetails;
    private List<ProductDTO> products;


}
