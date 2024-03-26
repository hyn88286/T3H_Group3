package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@Entity
@Table(name = "cart")
public class CartEntity extends AbstractEntity{
    private int quantity;
    private Double total;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "size_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SizeEntity sizeEntity;
}
