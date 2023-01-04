package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Optional<Product> findProductById(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM products WHERE category_id = :id")
    List<Product> findAllByCategoryId(@Param("id") Long id);
    @Query(nativeQuery = true, value = "SELECT quantity_in_stock FROM products WHERE id = :id")
    int getQuantityInStock(@Param("id") Long id);
    @Query(nativeQuery = true, value = "SELECT category_id, name From products ORDER BY category_id ASC, name ASC;")
    List<Product> sortedProductByCategoryAndNameAsc();
    @Query(nativeQuery = true, value = "SELECT * FROM products " +
            "JOIN categories c on products.category_id = c.id " +
            "WHERE c.company_id = :compId AND products.is_deleted = FALSE " +
            "ORDER BY description, name;")
    List<Product> findAllByCompanyId(@Param("compId")Long id);

    Optional<Product> findProductByName(String name);
}
