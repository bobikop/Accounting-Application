package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List<ProductDTO> listAllProducts();
    void save(ProductDTO dto);
    void update(ProductDTO dto);
    void deleteById(Long id);
}