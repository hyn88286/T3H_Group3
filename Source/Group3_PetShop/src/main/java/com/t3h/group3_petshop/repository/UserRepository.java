package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    @Query(value = "select u from UserEntity u where u.deleted=false")
    List<UserEntity> userId();
}
