package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ProductDTO;

public interface ProductService {

    void save(ProductDTO dto);
    void update(ProductDTO dto);
    void deleteById(Long id);


}
/*

product_create.html
product_list.html
product_update.html
 */