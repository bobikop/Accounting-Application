package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CompanyRepository;
import com.thegogetters.accounting.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<CompanyDto> listAll() {

        List<Company> list = companyRepository.findAll();

        List<CompanyDto> companyDtoList =list.stream().map(company -> mapperUtil.convert(company,new CompanyDto())).collect(Collectors.toList());

        return companyDtoList;
    }


}
