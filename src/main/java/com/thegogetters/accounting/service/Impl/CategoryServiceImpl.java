package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CategoryRepository;
import com.thegogetters.accounting.service.CategoryService;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, CompanyService companyService, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.productService = productService;
    }

    @Override
    public List<CategoryDto> listCategories() {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        List<CategoryDto> categoryList = categoryRepository.listCategoriesByAscOrder().stream()
                .filter(category -> category.getCompany().getId().equals(companyDto.getId()))
                .map(category -> mapperUtil.convert(category, new CategoryDto())).toList();

        for (CategoryDto category : categoryList) {
            if(productService.checkAnyProductExist(category.getId())) category.setHasProduct(true);
        }
        return categoryList;
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public void updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow();
        CategoryDto toBeConverted = mapperUtil.convert(category, new CategoryDto());
        toBeConverted.setId(category.getId());
        toBeConverted.setDescription(categoryDto.getDescription());
        categoryRepository.save(mapperUtil.convert(toBeConverted, new Category()));
    }

    @Override
    public CategoryDto findByDescription(String description) {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        return mapperUtil.convert(categoryRepository.findByDescriptionAndCompanyId(description, companyDto.getId()), new CategoryDto());
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        categoryDto.setCompany(companyDto);
        Category category = mapperUtil.convert(categoryDto, new Category());
        if(!ifCategoryExist(category.getDescription())) categoryRepository.save(category);
    }

    @Override
    public boolean ifCategoryExist(String description) {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        if(categoryRepository.findByDescriptionAndCompanyId(description, companyDto.getId())==null)return false;
        return categoryRepository.findByDescriptionAndCompanyId(description, companyDto.getId()).getCompany().getId().equals(companyDto.getId());
    }

    @Override
    public CategoryDto checkAndSetProductStatus(Long id) {
        CategoryDto categoryDto = findById(id);
        if(productService.checkAnyProductExist(categoryDto.getId())) categoryDto.setHasProduct(true);
        return categoryDto;
    }


}
