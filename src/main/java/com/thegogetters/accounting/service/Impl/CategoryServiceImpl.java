package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CategoryRepository;
import com.thegogetters.accounting.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<CategoryDto> listCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public void updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryDto toBeConverted = mapperUtil.convert(category, new CategoryDto());
        toBeConverted.setId(category.getId());
        toBeConverted.setDescription(categoryDto.getDescription());
        categoryRepository.save(mapperUtil.convert(toBeConverted, new Category()));
    }

    @Override
    public CategoryDto findByDescription(String description) {
        return mapperUtil.convert(categoryRepository.findByDescription(description), new CategoryDto());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        Category category = mapperUtil.convert(categoryDto, new Category());
        categoryRepository.save(category);
    }

}
