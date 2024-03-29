package com.t3h.group3_petshop.repository;

import com.t3h.group3_petshop.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<ProductImageEntity, Long> {

}
