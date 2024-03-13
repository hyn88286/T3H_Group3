package com.t3h.group3_petshop.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_master")
public class OrderEntity extends AbstractEntity{

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;
}
