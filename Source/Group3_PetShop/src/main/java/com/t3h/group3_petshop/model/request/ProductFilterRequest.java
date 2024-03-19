package com.t3h.group3_petshop.model.request;

import lombok.Data;

@Data
public class ProductFilterRequest {
    private String name;
    private String code;
    private Float price;
    private Integer sizeId;
    private Integer categoryId ;
}