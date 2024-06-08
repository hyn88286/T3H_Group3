package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.CategoryEntity;
import com.t3h.group3_petshop.entity.SizeEntity;
import com.t3h.group3_petshop.model.request.CategoryFilterRequest;
import com.t3h.group3_petshop.model.request.SizeFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

    @Query(value = "SELECT s FROM SizeEntity s " +
            " WHERE " +
            "(:#{#condition.code} is null or lower(s.code) = :#{#condition.code} )" +
            "AND s.deleted=false ORDER BY s.createdDate desc "
    )
    Page<SizeEntity> findAllByFilter(@Param("condition") SizeFilterRequest filterRequest, Pageable pageable);
    @Query(value = "select s from SizeEntity s where s.id in :ids and s.deleted=false order by s.createdDate")
    Set<SizeEntity> findByIds(Set<Long> ids);
}