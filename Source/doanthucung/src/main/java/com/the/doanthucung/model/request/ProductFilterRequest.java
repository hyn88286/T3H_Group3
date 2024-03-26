package com.the.doanthucung.model.request;


import lombok.Data;

@Data
public class ProductFilterRequest {
    private String name;
    private String description;
    private Float price;
    private Long quantity;
    private String code;
    private Integer categryId;
    private Integer sizeId;
}
