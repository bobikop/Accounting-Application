package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);
    List<ProductDTO> listAllProducts();
    void save(ProductDTO productDTO);
    void update(ProductDTO productDTO);
    void deleteById(Long id);
    boolean checkAnyProductExist(Long id);
}