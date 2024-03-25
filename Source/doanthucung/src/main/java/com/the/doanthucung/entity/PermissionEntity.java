package com.the.doanthucung.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class PermissionEntity extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "lastmodify_by")
    private UserEntity lastModifiedBy;

}
