package com.thegogetters.accounting.mapper;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Category convertToEntity(CategoryDto dto){
        return modelMapper.map(dto,Category.class);

    }

    public CategoryDto convertToDto(Category entity){

        return modelMapper.map(entity,CategoryDto.class);
    }
}
