package com.thegogetters.accounting.service;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.dto.ProductDTO;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.enums.InvoiceType;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id) throws AccountingAppException;
    List<ProductDTO> listAllProducts();
    ProductDTO save(ProductDTO productDTO);
    void update(ProductDTO productDTO) throws AccountingAppException;
    void deleteById(Long id) throws AccountingAppException;
    boolean checkAnyProductExist(Long id);
    boolean isInStockEnough(InvoiceProductDTO invoiceProductDTO);
    List<ProductDTO> getAllProductsByCompany();
    boolean checkAnyInvoiceExist(Long id);
    boolean isNameExist(String name, Long id);
    void updateProductQuantity(InvoiceType invoiceType, InvoiceProduct invoiceProduct);
}