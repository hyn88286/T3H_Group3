package com.the.doanthucung.entity;

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
    private Float price;
    private int quantity;



    @ManyToOne
    @JoinColumn(name = "category")
    @ToString.Exclude
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @ToString.Exclude
    private UserEntity createdByEntity;

    @ManyToOne
    @JoinColumn(name = "lastmodify_by")
    @ToString.Exclude
    private UserEntity lastModifiedByEntity;


    @ManyToMany(mappedBy = "products")
    private Set<SizeEntity> sizeEntities;

}
