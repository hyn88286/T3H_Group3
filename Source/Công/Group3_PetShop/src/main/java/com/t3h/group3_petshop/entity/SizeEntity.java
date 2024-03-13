package com.t3h.group3_petshop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "size")
@Data
public class SizeEntity extends AbstractEntity{

    private String name;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "product_size",
    joinColumns = {@JoinColumn(name = "size_id")},
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductSizeEntity> productSizeEntities;
}
