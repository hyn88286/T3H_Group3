package com.t3h.group3_petshop.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ProductDTO {
    // ID
    private Long id;
    // Code
    private String code;
    // Tên
    private String name;
    // Giá
    private Double price;
    // Giá min
    private Double minPrice;
    // Giá max
    private Double maxPrice;
    // Mô tả
    private String description;
    // Mô tả ngắn
    private String shortDescription;
    // Danh sách ảnh
    private Set<ProductImageDTO> images;
    // Url ảnh
    private String urlImage;
    // Số lượng
    private int quantity;
    // Danh mục
    private String category;
    // Id danh mục
    private Long categoryId;
    // Danh sách size
    private Set<SizeDTO> sizes;
    // Danh sách id size
    private List<Long> sizeIds;
    // Id size active
    private Long sizeId;
    // Deleted
    private Boolean deleted;
    // Ngày tạo
    private LocalDateTime createDate;
    // Tên tài khoản tạo
    private String createBy;
    // Ngày chỉnh sửa gần nhất
    private LocalDateTime lastModifiedDate;
    // Người chỉnh sửa
    private String lastModifiedBy;
}
