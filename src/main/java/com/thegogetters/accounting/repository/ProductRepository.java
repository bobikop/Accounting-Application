package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findAll();
    Product findProductById(Long id);

    List<Product> findAllByCategoryId(Long id);
}
