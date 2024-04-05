package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.*;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    @Query(value = "select u from UserEntity u where u.deleted=false")
    List<UserEntity> userId();

//    @Query(value = "SELECT u FROM UserEntity u " +
//            "LEFT JOIN u.roles r " +
//            " WHERE " +
//            " (:#{#condition.} is null or lower(u.) = :#{#condition.code}) " +
//            "AND (:#{#condition.sizeId} is null or u.id = :#{#condition.sizeId} ) " +
//            "AND p.deleted=false"
//    )
//    Page<UserEntity> findAllByFilter(@Param("condition") ProductFilterRequest filterRequest, Pageable pageable);
}
