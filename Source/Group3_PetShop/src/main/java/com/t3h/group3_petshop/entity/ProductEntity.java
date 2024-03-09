package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;



@Table(name = "product")
@Entity
@Data
public class ProductEntity extends AbstractEntity{
    private String name;
    private String description;
    private String code;
    @Column(name = "short_description")
    private String shortDescription;
    private Double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private CategoryEntity categoryEntity;

}
