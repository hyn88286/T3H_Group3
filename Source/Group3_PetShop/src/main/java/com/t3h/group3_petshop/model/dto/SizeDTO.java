package com.t3h.group3_petshop.model.dto;

import lombok.Data;

@Data
public class SizeDTO {
    // ID
    private Long id;
    // Tên size
    private String name;
    // Khối lượng
    private Integer weight;
}
