package com.the.doanthucung.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "category")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity extends AbstractEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @ToString.Exclude
    private UserEntity createdByEntity;

    @ManyToOne
    @JoinColumn(name = "lastmodify_by")
    @ToString.Exclude
    private UserEntity lastModifiedByEntity;

}
