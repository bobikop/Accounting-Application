package com.thegogetters.accounting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id;
    private String description;
    //    private CompanyDto company;
    private boolean hasProduct;
}
