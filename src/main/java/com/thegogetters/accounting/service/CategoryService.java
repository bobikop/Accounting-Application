package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();

    CategoryDto findById(Long id);

    void updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto createCategory(CategoryDto categoryDto);

    boolean ifCategoryExist(String description);

    CategoryDto checkAndSetProductStatus(Long id);
}
