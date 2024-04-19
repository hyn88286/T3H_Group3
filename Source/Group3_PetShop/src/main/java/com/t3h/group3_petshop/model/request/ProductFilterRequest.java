package com.t3h.group3_petshop.model.request;

import lombok.Data;

@Data
public class ProductFilterRequest {
    private String name;
    private String code;
    private Float price;
    private Long sizeId;
    private Integer categoryId;
    private String created;
}