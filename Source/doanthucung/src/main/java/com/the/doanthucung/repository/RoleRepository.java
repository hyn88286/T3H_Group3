package com.the.doanthucung.repository;

import com.the.doanthucung.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    @Query(value = "select r from RoleEntity r join r.userEntities u where u.username=:username")
    List<RoleEntity> getRoleByUsername(@Param("username") String username);
}
