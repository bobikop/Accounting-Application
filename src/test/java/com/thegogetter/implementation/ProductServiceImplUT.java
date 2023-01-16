package com.thegogetter.implementation;

import com.thegogetter.TestDocumentInitializer;
import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.entity.Product;
import com.thegogetters.accounting.enums.CompanyStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ProductRepository;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.Impl.ProductServiceImpl;
import com.thegogetters.accounting.service.InvoiceProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUT {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    CompanyService companyService;
    @Mock
    InvoiceProductService invoiceProductService;
    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());
    ProductDTO initProductDTO;

    @BeforeEach
    void setUp() {
        initProductDTO = TestDocumentInitializer.getProduct();
    }

    @Test
    @DisplayName("test_list_all_product")
    void listAllProducts() {
        // Given
        ProductDTO productDTO1 = initProductDTO;
        ProductDTO productDTO2 = TestDocumentInitializer.getProduct();
        Sort sort = Sort.by("category_id", "name").ascending();

        List<Product> productList = Stream.of(productDTO1, productDTO2)
                .map(product -> mapperUtil.convert(product, new Product())).toList();
        // When
        when(productRepository.findAll(sort)).thenReturn(productList);
        List<ProductDTO> allProducts = productService.listAllProducts();
        // Then
        assertThat(allProducts.size() == 2);
    }

    @Test
    @DisplayName("test_get_product_by_id")
    void testGetProductById() throws AccountingAppException {
        //Given
        Product product = mapperUtil.convert(initProductDTO, new Product());
        //When
        when(productRepository.findProductById(initProductDTO.getId())).thenReturn(Optional.of(product));
        ProductDTO testProduct = productService.getProductById(initProductDTO.getId());
        //Then
        assertThat(testProduct.getId().equals(product.getId()));
    }

    @Test
    @DisplayName("test_get_product_by_id_then_fail_if_id_not_found")
    void testGetProductByIdThenFailIfIdNotFound() {
        //When
        when(productRepository.findProductById(anyLong())).thenThrow(AccountingAppException.class);
        //Then
        assertThrows(AccountingAppException.class, () -> productService.getProductById(anyLong()));
    }

    @Test
    @DisplayName("test_save")
    void testSave() {
        //Given
        Product product = mapperUtil.convert(initProductDTO, new Product());

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(companyDto);
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        //When
        product.setCompany(company);
        ProductDTO testSave = productService.save(initProductDTO);
        //Then
        assertNotNull(testSave);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("test_update_product")
    void testUpdateProduct() {
        //Given
        Product product = mapperUtil.convert(initProductDTO, new Product());
        ProductDTO updatedProductDTO = mapperUtil.convert(product, new ProductDTO());

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(companyDto);
        Company company = mapperUtil.convert(companyService.getCompanyOfLoggedInUser(), new Company());
        //When
        product.setName("New_Test_Product");
        product.setCompany(company);
        when(productRepository.findProductById(anyLong())).thenReturn(Optional.of(product));
        productService.update(updatedProductDTO);
        //Then
        assertThat(productService.getProductById(anyLong()).getName()).isEqualTo("New_Test_Product");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("test_delete_by_id")
    void testDeleteById() {
        Product product = mapperUtil.convert(initProductDTO, new Product());
        product.setIsDeleted(true);
        when(productRepository.findProductById(anyLong())).thenReturn(Optional.of(product));
        productService.deleteById(initProductDTO.getId());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("check_any_product_exist")
    void checkAnyProductExist() {
        Product product = mapperUtil.convert(initProductDTO, new Product());
        List<Product> products = List.of(product);
        when(productRepository.findAllByCategoryId(anyLong())).thenReturn(products);
        assertTrue(productService.checkAnyProductExist(anyLong()));
    }

    @Test
    @DisplayName("is_in_stock_enough")
    void isInStockEnough() {
        Product product = mapperUtil.convert(initProductDTO, new Product());
        InvoiceProductDTO invoiceProductDTO = TestDocumentInitializer.getInvoiceProduct();
        invoiceProductDTO.setQuantity(10);
        product.setQuantityInStock(20);
        when(productRepository.findProductByName(anyString())).thenReturn(product);
        assertTrue(productService.isInStockEnough(invoiceProductDTO));
    }

    @Test
    @DisplayName("get_all_products_by_company")
    void getAllProductsByCompany() {
        //Given
        ProductDTO productDTO1 = initProductDTO;
        ProductDTO productDTO2 = TestDocumentInitializer.getProduct();
        Sort sort = Sort.by("company_id", "category_id", "name").ascending();

        List<Product> productList = Stream.of(productDTO1, productDTO2)
                .map(product -> mapperUtil.convert(product, new Product())).toList();
        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        Company company = mapperUtil.convert(companyDto, new Company());
        // When
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(companyDto);
        when(productRepository.findAllByCompanyId(company.getId(), sort)).thenReturn(productList);
        List<ProductDTO> allProducts = productService.getAllProductsByCompany();
        // Then
        assertEquals(2, allProducts.size());
    }

    @Test
    @DisplayName("check_any_invoice_exist_false")
    void checkAnyInvoiceExistFalse() {
        when(invoiceProductService.findInvoiceProductsByProductID(1L)).thenReturn(anyList());
        assertFalse(productService.checkAnyInvoiceExist(1L));
    }

    @Test
    @DisplayName("check_any_invoice_exist_true")
    void checkAnyInvoiceExistTrue() {
        List<InvoiceProductDTO> invoiceProductDTOList = List.of(TestDocumentInitializer.getInvoiceProduct());
        when(invoiceProductService.findInvoiceProductsByProductID(1L)).thenReturn(invoiceProductDTOList);
        assertTrue(productService.checkAnyInvoiceExist(1L));
    }

    @Test
    @DisplayName("is_name_exist")
    void isNameExist() {
        //Given
        Product product = mapperUtil.convert(initProductDTO, new Product());
        //When
        when(productRepository.findProductByName(product.getName())).thenReturn(product);
        //Then
        assertTrue(productService.isNameExist(product.getName(), anyLong()));
        verify(productRepository, times(1)).findProductByName(anyString());
    }

    @Test
    @DisplayName("update_product_quantity")
    void updateProductQuantity() {
        //Given
        InvoiceProduct invoiceProduct = mapperUtil.convert(TestDocumentInitializer.getInvoiceProduct(), new InvoiceProduct());
        invoiceProduct.getInvoice().setInvoiceType(InvoiceType.PURCHASE);
        //When
        when(productRepository.save(any(Product.class))).thenReturn(invoiceProduct.getProduct());
        //Then
        productService.updateProductQuantity(InvoiceType.PURCHASE, invoiceProduct);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("get_quantity_by_id")
    void getQuantityById() {
        //Given
        int qtyInStock = 1;
        //When
        when(productRepository.getQuantityInStock(anyLong())).thenReturn(qtyInStock);
        //Then
        int quantityById = productService.getQuantityById(anyLong());
        assertThat(quantityById).isEqualTo(1);
    }

    @Test
    @DisplayName("get_all_products_by_category_id")
    void getAllProductsByCategoryId() {
        //Given
        ProductDTO productDTO1 = initProductDTO;
        ProductDTO productDTO2 = TestDocumentInitializer.getProduct();

        List<Product> productList = Stream.of(productDTO1, productDTO2)
                .map(product -> mapperUtil.convert(product, new Product())).toList();
        // When
        when(productRepository.findAllByCategoryId(anyLong())).thenReturn(productList);
        List<ProductDTO> allProducts = productService.getAllProductsByCategoryId(anyLong());
        // Then
        assertEquals(2, allProducts.size());
    }
}