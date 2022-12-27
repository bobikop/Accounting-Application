package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIsDeleted(Boolean isDeleted);

    Category findByIdAndIsDeleted(Long id, Boolean isDeleted);
    Category findByDescription(String description);
}
