package com.thegogetters.accounting.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private long id;

    @NotBlank
    @Size(max = 100, min = 2)
    private String description;

    private CompanyDto company;
    private boolean hasProduct;
}
