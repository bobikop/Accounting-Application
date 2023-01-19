package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    List<Company> findByIdIsNot(Long id);
}
