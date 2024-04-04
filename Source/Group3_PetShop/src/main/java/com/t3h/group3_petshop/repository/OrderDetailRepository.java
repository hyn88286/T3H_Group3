package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.OrderDetailEntity;
import com.t3h.group3_petshop.entity.OrderEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    @Query(value = "SELECT od FROM OrderDetailEntity od " +
            "LEFT JOIN od.productEntity p " +
            "LEFT JOIN od.sizeEntity s" +
            " WHERE " +
            " (:#{#orderId} is null or od.orderEntity.id = :#{#orderId}) " +
            "AND p.deleted=false"
    )
    List<OrderDetailEntity> findByCode(Long orderId);
}
