package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.OrderDetailEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Long> {
    @Query(value = "SELECT o FROM OrderDetailEntity o " +
            "LEFT JOIN o.orderEntity u " + // Thay đổi userEntity thành user
            "LEFT JOIN o.productEntity p " +
            "WHERE " +
            "(:#{#userId} is null or u.userEntity.id = :#{#userId})" +
            " AND (:#{#productId} is null or p.id = :#{#productId} )" +
            "AND o.deleted=false ORDER BY o.createdDate desc ")
    OrderDetailEntity findOrderEntitiesBy(Long userId, Long productId);


}
