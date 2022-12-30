package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findAll();
    Product findProductById(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE category_id = :id")
    List<Product> findAllByCategoryId(@Param("id") Long id);
    @Query(nativeQuery = true, value = "SELECT quantity_in_stock FROM products WHERE id = :id")
    int getQuantityInStock(@Param("id") Long id);
}
