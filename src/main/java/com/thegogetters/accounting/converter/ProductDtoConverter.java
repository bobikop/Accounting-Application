package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoConverter implements Converter<String, ProductDTO> {
    private final ProductService productService;

    public ProductDtoConverter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }
        try {
            return productService.getProductById(Long.valueOf(source));
        } catch (AccountingAppException e) {
            throw new RuntimeException(e);
        }
    }
}