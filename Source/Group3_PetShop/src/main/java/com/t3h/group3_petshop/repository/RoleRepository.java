package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    @Query(value = "select r from RoleEntity r join r.users u where u.username=:username")
    List<RoleEntity> getRoleByUsername(@Param("username") String username);

    @Query(value = "select r from RoleEntity r where r.name in :names and r.deleted=false")
    List<RoleEntity> findRoleByNames(List<String> names);

}