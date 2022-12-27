package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByDescription(String description);
}
