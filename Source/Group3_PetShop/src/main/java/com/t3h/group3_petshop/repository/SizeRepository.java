package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

    @Query(value = "select s from SizeEntity s where s.id in :ids and s.deleted=false")
    Set<SizeEntity> findByIds(Set<Long> ids);
}