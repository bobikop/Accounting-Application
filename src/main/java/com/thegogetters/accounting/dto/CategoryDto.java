package com.thegogetters.accounting.dto;

import lombok.*;

import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id;
    private String description;
    @ManyToOne
    private CompanyDto company;
    private boolean hasProduct;
}
