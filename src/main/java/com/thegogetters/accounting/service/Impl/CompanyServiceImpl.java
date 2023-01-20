package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.CompanyStatus;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.CompanyRepository;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.SecurityService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SecurityService securityService;

    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }

    //=========================================================================//
    @Override
    public List<CompanyDto> listAll() {

        List<Company> list = companyRepository.findByIdIsNot(1L);

        List<CompanyDto> companyDtoList = list.stream().
                map(company -> mapperUtil.convert(company, new CompanyDto())).
                sorted(comparing(CompanyDto::getTitle))
                .sorted(comparing(CompanyDto::getCompanyStatus))
                .collect(Collectors.toList());


        return companyDtoList;
    }

    //=========================================================================//
    @Override
    public CompanyDto findById(Long id) throws AccountingAppException {

        Company company = companyRepository.findById(id).orElseThrow(()-> new AccountingAppException("Company not found"));


        return mapperUtil.convert(company, new CompanyDto());
    }

    //=========================================================================//
    @Override
    public void save(CompanyDto companyDto) {

        if (companyDto.getCompanyStatus() == null) {

            companyDto.setCompanyStatus(CompanyStatus.PASSIVE);

        }

        companyRepository.save(mapperUtil.convert(companyDto, new Company()));
    }

    //=========================================================================//
    @Override
    public void changeCompanyStatusById(Long id) throws AccountingAppException {

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
    public CompanyDto update(CompanyDto companyDto) throws AccountingAppException {

        CompanyDto oldCompany = findById(companyDto.getId());

        companyDto.setCompanyStatus(oldCompany.getCompanyStatus());

        companyRepository.save(mapperUtil.convert(companyDto, new Company()));

        return findById(companyDto.getId());
    }

    @Override
    public CompanyDto getCompanyOfLoggedInUser() {

        return securityService.getLoggedInUser().getCompany();
    }
    //=========================================================================//


    @Override
    public List<CompanyDto> listAllByUser() {

        if (securityService.getLoggedInUser().getRole().getId() == 1L){
            return listAll();
        }
        if (securityService.getLoggedInUser().getRole().getId() == 2L){
            return listAll().stream()
                    .filter(companyDto -> companyDto.getId().equals(securityService.getLoggedInUser().getCompany().getId()))
                    .collect(Collectors.toList());
        }
        return null;

    }

}
