package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@Entity
@Table(name = "cart")
public class CartEntity extends AbstractEntity{

    // ID người dùng
    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserEntity userEntity;

    // ID sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity productEntity;

    // ID size
    @ManyToOne
    @JoinColumn(name = "size_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private SizeEntity sizeEntity;

    // Số lượng sản phẩm
    Integer quantity;

    // Tổng tiền
    Double total;
}