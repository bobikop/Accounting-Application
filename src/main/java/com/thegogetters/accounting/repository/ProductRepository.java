package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
