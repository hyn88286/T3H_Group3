package com.t3h.group3_petshop.repository;
import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.OrderEntity;
import com.t3h.group3_petshop.entity.UserEntity;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.request.OrderFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    @Query(value = "SELECT o FROM CategoryEntity o " +
            "LEFT JOIN o.productEntities u " +
            " WHERE " +
            "(:#{#condition.productId} is null or u.id = :#{#condition.productId} )" +
            "AND o.deleted=false ORDER BY o.createdDate desc "
    )
    Page<CategoryEntity> findAllByFilter(@Param("condition") CategoryFilterRequest filterRequest, Pageable pageable);
}
