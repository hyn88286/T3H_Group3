package com.the.doanthucung.repository;


import com.the.doanthucung.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity,Long> {
    @Query(value = "select s from SizeEntity s where s.id in :ids and s.delete=false")
    Set<SizeEntity>finByIds(List<Long> ids);
}