package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.ProductEntity;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT DISTINCT p FROM ProductEntity p " +
            "LEFT JOIN p.categoryEntity c " +
            "LEFT JOIN p.sizeEntities s " +
            " WHERE " +
            " (:#{#condition.name} is null or lower(p.name) = :#{#condition.name}) " +
            "AND (:#{#condition.code} is null or p.code = :#{#condition.code} )" +
            "AND (:#{#condition.price} is null or p.price = :#{#condition.price} )" +
            "AND (:#{#condition.sizeId} is null or s.id = :#{#condition.sizeId} ) " +
            "AND (:#{#condition.categoryId} is null or c.id = :#{#condition.categoryId} )" +
            "AND p.deleted=false"
    )
    Page<ProductEntity> findAllByFilter(@Param("condition") ProductFilterRequest filterRequest, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity p " +
            "LEFT JOIN p.categoryEntity c " +
            "LEFT JOIN p.sizeEntities s " +
            " WHERE " +
            " (:#{#condition.code} is null or lower(p.code) = :#{#condition.code}) " +
            "AND (:#{#condition.sizeId} is null or s.id = :#{#condition.sizeId} ) " +
            "AND p.deleted=false ORDER BY p.createdDate desc LIMIT 1"
    )
    ProductEntity findByFilter(@Param("condition") ProductFilterRequest filterRequest);
}
