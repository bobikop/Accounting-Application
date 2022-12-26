package com.thegogetters.accounting.entity;

import com.thegogetters.accounting.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private int quantityInStock;

    private int lowLimitAlert;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
