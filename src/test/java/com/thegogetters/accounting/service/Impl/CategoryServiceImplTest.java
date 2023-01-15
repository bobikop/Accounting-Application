package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.*;
import com.thegogetters.accounting.entity.Category;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.CompanyStatus;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CategoryRepository;
import com.thegogetters.accounting.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    ProductServiceImpl productService;
    @Mock
    CompanyServiceImpl companyService;
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImpl categoryService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    @Test
    void listCategories() {
        //GIVEN
        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        CompanyDto companyDto = new CompanyDto();
        UserDTO userDTO = new UserDTO();
        userDTO.setCompany(companyDto);
        categoryDto.setCompany(companyDto);
        Category category = mapperUtil.convert(categoryDto, new Category());

        assertEquals(category.getId(), categoryDto.getId());

    }

    @Test
    void findById() throws AccountingAppException {
        //GIVEN
        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        //WHEN
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        //THEN
        categoryService.findById(categoryDto.getId());
        verify(categoryRepository).findById(anyLong());
    }

    @Test
    void updateCategory() {

        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        categoryDto.setId(1L);
        Category convertedCategory = mapperUtil.convert(categoryDto, new Category());
        convertedCategory.setId(categoryDto.getId());
        convertedCategory.setDescription(categoryDto.getDescription());

        assertEquals(categoryDto.getId(), convertedCategory.getId());
    }

    @Test
    void deleteCategory() throws AccountingAppException {
        //GIVEN
        //WHEN
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        //THEN
        categoryService.deleteCategory(anyLong());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory() {

        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        categoryDto.setId(1L);

        when(companyService.getCompanyOfLoggedInUser()).thenReturn(new CompanyDto());

        categoryService.createCategory(categoryDto);

        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void ifCompanyIdEqualUserCompanyIdThenSuccess(){
        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");
        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        categoryDto.setCompany(companyDto);

        assertEquals(userDTO.getCompany().getId(), categoryDto.getCompany().getId());
    }

    @Test
    void getQuantityInStockByCategoryId(){

        CategoryDto categoryDto = TestDocumentInitializer.getCategory();
        categoryDto.setId(1L);

        ProductDTO productDTO = TestDocumentInitializer.getProduct();
        productDTO.setId(1L);
        productDTO.setCategory(categoryDto);

        assertFalse(categoryService.getQuantityInStockByCategoryId(1L) > 0);
    }

    @Test
    void whenCategoryCannotFoundThenThrow(){

        when(categoryRepository.findById(anyLong())).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> categoryService.findById(anyLong()));
    }
}