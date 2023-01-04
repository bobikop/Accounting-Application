package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    //List<ClientVendorDto> findAllByClientVendorType(ClientVendorType clientVendorType);

    ClientVendorDto findById(long id);
    List<ClientVendorDto> findAllByClientVendorTypeBelongsToCompany(ClientVendorType vendor);
    List<ClientVendorDto> listAll();
    void update(ClientVendorDto clientVendorDto);
    void deleteById(Long id);
    void save(ClientVendorDto clientVendorDto);

    boolean isClientVendorCanBeDeleted(Long id);

    List<ClientVendorDto> findAllByCompany();







}
