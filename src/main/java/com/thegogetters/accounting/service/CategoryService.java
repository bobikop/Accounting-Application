package com.thegogetters.accounting.service;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();

    CategoryDto findById(Long id) throws AccountingAppException;

    void updateCategory(CategoryDto categoryDto) throws AccountingAppException;

    void deleteCategory(Long id) throws AccountingAppException;

    CategoryDto createCategory(CategoryDto categoryDto);

    boolean ifCategoryExist(String description);

    CategoryDto checkAndSetProductStatus(Long id) throws AccountingAppException;

    int getQuantityInStockByCategoryId(Long id);
}
