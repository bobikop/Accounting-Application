package com.thegogetters.accounting.dto;

import com.thegogetters.accounting.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class TestDocumentInitializer {

    public static UserDTO getUser(String role){
        return UserDTO.builder()
                .id(1L)
                .firstname("John")
                .lastname("Mike")
                .phone("+1 (111) 111-1111")
                .password("Abc1")
                .confirmPassword("Abc1")
                .role(new RoleDTO(1L,role))
                .isOnlyAdmin(false)
                .company(getCompany(CompanyStatus.ACTIVE))
                .build();
    }

    public static CompanyDto getCompany(CompanyStatus status){
        return CompanyDto.builder()
                .title("Test_Company")
                .website("www.test.com")
                .id(1L)
                .phone("+1 (111) 111-1111")
                .companyStatus(status)
                .address(new AddressDto())
                .build();
    }

    public static CategoryDto getCategory(){
        return CategoryDto.builder()
                .company(getCompany(CompanyStatus.ACTIVE))
                .description("Test_Category")
                .build();
    }

    public static ClientVendorDto getClientVendor(ClientVendorType type){
        return ClientVendorDto.builder()
                .clientVendorType(type)
                .clientVendorName("Test_ClientVendor")
                .address(new AddressDto())
                .website("https://www.test.com")
                .phone("+1 (111) 111-1111")
                .build();
    }

    public static ProductDTO getProduct(){
        return ProductDTO.builder()
                .category(getCategory())
                .productUnit(ProductUnit.PCS)
                .name("Test_Product")
                .quantityInStock(10)
                .lowLimitAlert(5)
                .build();
    }

    public static InvoiceProductDTO getInvoiceProduct(){
        return InvoiceProductDTO.builder()
                .product(getProduct())
                .price(BigDecimal.TEN)
                .tax(10)
                .quantity(10)
                .invoice(new InvoiceDTO())
                .build();
    }

    public static InvoiceDTO getInvoice(InvoiceStatus status, InvoiceType type){
        return InvoiceDTO.builder()
                .invoiceNo("T-001")
                .clientVendor(getClientVendor(ClientVendorType.CLIENT))
                .invoiceStatus(status)
                .invoiceType(type)
                .date(LocalDate.of(2022,01,01))
                .company(getCompany(CompanyStatus.ACTIVE))
                .invoiceProducts(new ArrayList<>(Arrays.asList(getInvoiceProduct())))
                .price(BigDecimal.valueOf(1000))
                .tax(10)
                .total(BigDecimal.TEN.multiply(BigDecimal.valueOf(1000)))
                .build();
    }
}
