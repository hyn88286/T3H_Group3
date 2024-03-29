package com.t3h.group3_petshop.model.dto;

import lombok.Data;

@Data
public class ProductImageDTO {
    // ID
    private Long id;
    // Tên ảnh
    private String name;
    // Kiểu ảnh
    private String type;
    // Url dẫn đến ảnh ở local
    private String urlImage;
    // id sản phẩm
    private Long productId;
}
