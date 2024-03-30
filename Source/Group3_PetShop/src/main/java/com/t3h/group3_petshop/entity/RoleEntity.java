package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class RoleEntity extends AbstractEntity {
    private String name;

    private String code;
    @ManyToMany(mappedBy = "roleEntities")
    private List<UserEntity> userEntities;
}
