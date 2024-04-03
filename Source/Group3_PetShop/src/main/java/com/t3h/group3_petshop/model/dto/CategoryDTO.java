package com.t3h.group3_petshop.model.dto;


import lombok.Data;

@Data

public class CategoryDTO {
    private String code;
    private Long id;
    private String name;
    private Long productId;
}
