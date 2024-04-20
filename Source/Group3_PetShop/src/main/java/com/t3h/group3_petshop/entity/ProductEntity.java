package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

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
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private CategoryEntity categoryEntity;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "productEntities",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<SizeEntity> sizeEntities;

}
