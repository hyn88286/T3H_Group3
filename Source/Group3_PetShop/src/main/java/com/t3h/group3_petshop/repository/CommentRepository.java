package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.CommentEntity;
import com.t3h.group3_petshop.model.request.CommentFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "SELECT c FROM CommentEntity c " +
            "JOIN c.userEntity userEntity " + // Sử dụng tên biến trong entity
            "JOIN c.productEntity productEntity " + // Sử dụng tên biến trong entity
            "WHERE (:#{#filter.userId} IS NULL OR userEntity.id = :#{#filter.userId}) " + // Sử dụng tên biến trong entity
            "AND (:#{#filter.productId} IS NULL OR productEntity.id = :#{#filter.productId})") // Sử dụng tên biến trong entity
    Page<CommentEntity> findByFilter(@Param("filter") CommentFilterRequest filter, Pageable pageable);

    // Thêm một phương thức tìm kiếm sử dụng userId và productId
    @Query(value = "SELECT c FROM CommentEntity c " +
            "JOIN c.userEntity userEntity " + // Sử dụng tên biến trong entity
            "JOIN c.productEntity productEntity " + // Sử dụng tên biến trong entity
            "WHERE (:userId = :userIdParam OR userEntity.id = :userIdParam) " + // Sử dụng tên biến trong entity
            "AND (:productId = :productIdParam OR productEntity.id = :productIdParam)") // Sử dụng tên biến trong entity
    Page<CommentEntity> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId, Pageable pageable);
}
