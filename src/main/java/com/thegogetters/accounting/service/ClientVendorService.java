package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    //List<ClientVendorDto> findAllByClientVendorType(ClientVendorType clientVendorType);

    ClientVendorDto findById(long id);
    List<ClientVendorDto> findAllByClientVendorTypeBelongsToCompany(ClientVendorType vendor);
    List<ClientVendorDto> listAll();
    void update(ClientVendorDto clientVendorDTO);
    void deleteById(Long id);
    void save(ClientVendorDto clientVendorDto);





}
