package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.mapper.CategoryMapper;
import com.thegogetters.accounting.repository.CategoryRepository;
import com.thegogetters.accounting.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return categoryMapper.convertToDto(category);
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryDto toBeConverted = categoryMapper.convertToDto(category);
        toBeConverted.setId(category.getId());
        toBeConverted.setDescription(categoryDto.getDescription());
        categoryRepository.save(categoryMapper.convertToEntity(toBeConverted));
    }

    @Override
    public CategoryDto findByDescription(String description) {
        return categoryMapper.convertToDto(categoryRepository.findByDescription(description));
    }
}
