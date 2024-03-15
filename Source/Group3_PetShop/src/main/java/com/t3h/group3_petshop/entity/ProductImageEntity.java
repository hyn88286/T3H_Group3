package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductImageEntity extends AbstractEntity {
    private String name;
    private String type;
    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private ProductEntity productEntity;
}
