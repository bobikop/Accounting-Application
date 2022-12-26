package com.thegogetters.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id;
    private String description;
    //    private CompanyDto company;
    private boolean hasProduct;
}
