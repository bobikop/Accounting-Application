package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();
}
