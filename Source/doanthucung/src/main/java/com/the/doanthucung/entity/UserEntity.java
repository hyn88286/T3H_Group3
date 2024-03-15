package com.the.doanthucung.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import javax.management.relation.Role;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class UserEntity extends AbstractEntity {
    private String username;
    private String password;
    private  String email;
    private String name;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roleEntities;
}
