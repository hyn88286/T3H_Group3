package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageEntity, Long> {
  Optional<ImageEntity> findByName(String fileName);
}
