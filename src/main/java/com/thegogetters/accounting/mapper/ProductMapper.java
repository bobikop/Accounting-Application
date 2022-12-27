package com.thegogetters.accounting.mapper;

import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product convertToEntity(ProductDTO dto){
        return modelMapper.map(dto, Product.class);
    }
    public ProductDTO convertToDto(Product entity){
        return modelMapper.map(entity, ProductDTO.class);
    }
}