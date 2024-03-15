package com.the.doanthucung.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "rating")
@Data
public class RatingEntity extends AbstractEntity{
    private Long rating;
    private String review;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProductEntity productEntity;

    @ManyToOne
    @JoinColumn(name = "create_by")
    @ToString.Exclude
    private UserEntity createdByEntity;

    @ManyToOne
    @JoinColumn(name = "lastmodify_by")
    @ToString.Exclude
    private UserEntity lastModifiedByEntity;

}
