package com.t3h.group3_petshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageEntity extends AbstractEntity{
    private String name;

    private String type;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
}
