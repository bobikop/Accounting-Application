package com.thegogetters.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

    String name;
    int quantityInStock;
    int lowLimitAlert;
//    @Enumerated(EnumType.STRING)
//    private ProductStatus productStatus;

}
