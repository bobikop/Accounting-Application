package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.CompanyStatus;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CompanyRepository;
import com.thegogetters.accounting.service.CompanyService;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    //=========================================================================//
    @Override
    public List<CompanyDto> listAll() {

        List<Company> list = companyRepository.findByIdIsNot(1L);

        List<CompanyDto> companyDtoList = list.stream().
                map(company -> mapperUtil.convert(company, new CompanyDto())).
                collect(Collectors.toList());

        companyDtoList.sort(Comparator.comparing(CompanyDto::getCompanyStatus));


        return companyDtoList;
    }
    //=========================================================================//
    @Override
    public CompanyDto findById(Long id) {

        Optional<Company> company = companyRepository.findById(id);
        // handle exception here

        CompanyDto companyDto = mapperUtil.convert(company, new CompanyDto());

        return companyDto;
    }
    //=========================================================================//
    @Override
    public void save(CompanyDto companyDto) {

        if (companyDto.getCompanyStatus() == null) {

            companyDto.setCompanyStatus(CompanyStatus.PASSIVE);

        }

        Company company = mapperUtil.convert(companyDto, new Company());

        companyRepository.save(company);
    }
    //=========================================================================//
    @Override
    public void changeCompanyStatusById(Long id) {

        CompanyDto companyDto = findById(id);

        if (companyDto.getCompanyStatus().equals(CompanyStatus.PASSIVE)) {
            companyDto.setCompanyStatus(CompanyStatus.ACTIVE);
            save(companyDto);
            return;
        }


        if (companyDto.getCompanyStatus().equals(CompanyStatus.ACTIVE)) {
            companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        }

        save(companyDto);

    }

    //=========================================================================//
    @Override
    public CompanyDto update(CompanyDto companyDto) {

        CompanyDto oldCompany = findById(companyDto.getId());

        companyDto.setCompanyStatus(oldCompany.getCompanyStatus());

        companyDto.getAddress().setCountry("Unite States");

        Company company = mapperUtil.convert(companyDto, new Company());

        companyRepository.save(company);

        return findById(companyDto.getId());
    }
    //=========================================================================//

}
