package com.t3h.group3_petshop.model.dto;

import com.t3h.group3_petshop.entity.OrderDetailEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private LocalDateTime createdDate;
    private String createdBY;
    private LocalDateTime modifiedDate;
    private String modifiedBy;

    private String code;
    private String name;
    private Float price;
    private String description;
    private String shortDescription;
    private String status;
    private List<Long> sizeIds;
    private String size;
    private Long categoryId;
    private String category;
    private int quantity;

    public ProductDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public ProductDTO() {
    }
}
