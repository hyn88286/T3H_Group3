package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.ProductImageEntity;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ProductImageEntity, Long> {
    @Query(value = "SELECT i FROM ProductImageEntity i " +
            "LEFT JOIN i.productEntity p " +
            " WHERE " +
            " (:#{#condition.productId}  is null or p.id = :#{#condition.productId}) " +
            "AND i.deleted=false  ORDER BY i.createdDate limit 1"
    )
    ProductImageEntity findFirstImageByProductId(@Param("condition") ProductImageRequest filterRequest);
}
