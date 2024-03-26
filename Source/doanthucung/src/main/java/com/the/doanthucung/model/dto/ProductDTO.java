package com.the.doanthucung.model.dto;

import com.the.doanthucung.entity.OrderDetailEntity;
import com.the.doanthucung.entity.OrderEntity;
import com.the.doanthucung.entity.SizeEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private String size;
    private Integer quantity;
    private String category;
    private List<SizeEntity> sizeEntities;
    private Long categoryId;
    private List<Long> sizeIds;
    private List<OrderDetailEntity> orderDetailEntities;
    private List<String> imagesColor = new ArrayList<>();
    public ProductDTO(Long id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public ProductDTO() {
    }


}
