package com.the.doanthucung.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class RoleEntity extends AbstractEntity {
    private String name;
    private String code;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @ToString.Exclude
    private UserEntity createdByEntity;

    @ManyToOne
    @JoinColumn(name = "modified")
    private UserEntity modifiedEntity;

    @ManyToMany(mappedBy = "roleEntities")
    private Set<UserEntity> userEntities;
}
