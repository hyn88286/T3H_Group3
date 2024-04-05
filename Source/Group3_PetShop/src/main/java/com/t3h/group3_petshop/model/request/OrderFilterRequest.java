package com.t3h.group3_petshop.model.request;

import com.t3h.group3_petshop.model.dto.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderFilterRequest {
    private Integer userId;
    private Integer sizeId;
    private String status;
    private Double totalAmount;
    private List<ProductDTO> products;


    private String code;
}
