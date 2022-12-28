package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.CompanyDto;

import java.util.List;

public interface CompanyService {


    //=========================================================================//
    List<CompanyDto> listAll();

    //=========================================================================//
    CompanyDto findById(Long id);

    //=========================================================================//
    void save(CompanyDto companyDto);

    //=========================================================================//
    void changeCompanyStatusById(Long id);

    //=========================================================================//
    CompanyDto update(CompanyDto companyDto);

    CompanyDto getCompanyOfLoggedInUser();


}
