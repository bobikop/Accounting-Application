package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByDescriptionAndCompanyId(String description, Long id);

    @Query("SELECT c FROM Category c ORDER BY lower(c.description) asc")
    List<Category> listCategoriesByAscOrder();
}
