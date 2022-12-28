package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CategoryRepository;
import com.thegogetters.accounting.service.CategoryService;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

    private final UserService userService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, CompanyService companyService, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public List<CategoryDto> listCategories() {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        return categoryRepository.listCategoriesByAscOrder().stream()
                .filter(category -> category.getCompany().getId().equals(companyDto.getId()))
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
