package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private Integer quantityInStock;  // This field belongs to product total quantity in stock
                                      // There might be more than 1 invoiceProduct or invoice
                                      // who will change this value ONCE it is APPROVED..
    private Integer lowLimitAlert;
    private ProductUnit productUnit;
    private CategoryDto category;


}
