package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "order_detail")
@Data
public class OrderDetailEntity extends AbstractEntity{
    // ID đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private OrderEntity orderEntity;

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
    private Integer quantity;

    // Tổng tiền
    private Double total;
}
