package com.the.doanthucung.repository;

import com.the.doanthucung.entity.ProductEntity;
import com.the.doanthucung.model.request.ProductFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {


    @Query(value = "SELECT p FROM ProductEntity p " +
    "LEFT JOIN p.categoryEntity c " +
    "LEFT JOIN p.sizeEntities s " +
    " where " +
    "(:#{#condition.name} is null or lower(p.name) = :#{#condition.name})" +
    "AND (:#{#condition.description} is null or p.description = :#{#condition.description})" +
    "AND (:#{#conditon.price} is null or p.price = :#{condition.price})" +
    "AND (:#{condition.code} is null or p.code = :#{condition.code})" +
    "AND (:#{condition.quantity} is null or  p.quantity = :#{condition.quantity})" +
    "AND (:#{condition.sizeId} is null or s.id = :#{condition.sizeId})" +
    "AND (:#{condition.categoryId} is null or c.id = :#{condition.categoryId})" +
    "AND p.delete = false  ORDER BY  p.createdDate desc ")
    Page<ProductEntity> findAllByFilter(@Param("condition")ProductFilterRequest filterRequest, Pageable pageable);

}
