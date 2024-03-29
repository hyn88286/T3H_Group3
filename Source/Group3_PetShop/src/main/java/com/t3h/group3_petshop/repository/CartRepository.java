package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.CartEntity;
import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.model.request.CartFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    @Query(value = "SELECT c FROM CartEntity c " +
            "LEFT JOIN c.userEntity u " +
            "LEFT JOIN c.productEntity p " +
            "LEFT JOIN c.sizeEntity s " +
            " WHERE " +
            " (:#{#userId} is null or u.id = :#{#userId}) " +
            "AND p.deleted=false ORDER BY p.createdDate desc "
    )
    Page<CartEntity> findAllByFilter(Long userId , Pageable pageable);
}


