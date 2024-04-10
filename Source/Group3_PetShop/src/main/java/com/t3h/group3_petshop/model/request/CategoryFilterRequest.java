package com.t3h.group3_petshop.model.request;

import lombok.Data;

@Data
public class CategoryFilterRequest {
    private String name;
    private String code;
    private Long productId;
}
