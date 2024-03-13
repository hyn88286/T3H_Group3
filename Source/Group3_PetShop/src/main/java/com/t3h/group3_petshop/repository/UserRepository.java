package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
