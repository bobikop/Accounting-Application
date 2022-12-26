package com.thegogetters.accounting.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    private String description;
    @ManyToOne
    private Company company;

}
