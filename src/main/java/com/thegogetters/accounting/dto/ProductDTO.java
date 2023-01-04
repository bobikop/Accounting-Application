package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product Name is required field.")
    @Size(max = 100, min = 2, message = "Product Name must be between 2 and 100 characters long.")
    private String name;

    private Integer quantityInStock;  // This field belongs to product total quantity in stock
    // There might be more than 1 invoiceProduct or invoice
    // who will change this value ONCE it is APPROVED..

    @NotNull(message = "Low Limit Alert is a required field.")
    @Min(value = 1, message = "Low Limit Alert should be at least 1.")
    private Integer lowLimitAlert;

    @NotNull(message = "Product Unit is a required field.")
    private ProductUnit productUnit;

    @NotNull(message = "Please select a category.")
    private CategoryDto category;
}
