package com.thegogetters.accounting.repository;

import com.thegogetters.accounting.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
