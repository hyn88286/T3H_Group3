package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.*;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import com.t3h.group3_petshop.model.request.ProductFilterRequest;
import com.t3h.group3_petshop.model.request.ProductImageRequest;
import com.t3h.group3_petshop.model.request.UserRequest;
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
//    @Query(value = "select u from UserEntity u where u.deleted=false")
//    List<UserEntity> userId();

    @Query(value = "SELECT e FROM UserEntity e " +
            "LEFT JOIN e.roles c " +
            " WHERE " +
            " (:#{#condition.userAId}  is null or c.id = :#{#condition.userAId}) " +
            "AND e.deleted=false  ORDER BY e.createdDate limit 1"
    )
   Page<UserEntity >findFirstByUserId(@Param("condition") UserRequest filterRequest, Pageable pageable);
}
