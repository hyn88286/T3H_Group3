package com.the.doanthucung.repository;

import com.the.doanthucung.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
}