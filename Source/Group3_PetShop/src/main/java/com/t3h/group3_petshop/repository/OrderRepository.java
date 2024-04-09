package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.OrderEntity;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT o FROM OrderEntity o " +
            "LEFT JOIN o.userEntity u " + // Thay đổi userEntity thành user
            "LEFT JOIN o.orderDetailEntity d " +
            "WHERE " +
            "(:#{#condition.userId} is null or u.id = :#{#condition.userId})" +
            " AND (:#{#condition.orderdtID} is null or u.id = :#{#condition.orderdtID} )" +
            "AND o.deleted=false ORDER BY o.createdDate desc "
    )
    Page<OrderEntity> findAllByFilter(@Param("condition") OrderFilterRequest filterRequest, Pageable pageable);

    @Query(value = "SELECT o FROM OrderEntity o " +
            "LEFT JOIN o.userEntity u " +
            " WHERE " +
            "(:#{#userId} is null or u.id = :#{#userId} )" +
            "AND (:#{#code} is null or lower(o.code) = :#{#code} )" +
            "AND (:#{#status} is null or o.status = :#{#status} )" +
            "AND o.deleted=false ORDER BY o.createdDate desc LIMIT 1"
    )
    OrderEntity getByCode(Long userId, String code, Integer status);
}
