package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.AddressDto;

import java.util.List;

public interface AddressService {


    List<String > findAllCountries();

    AddressDto update(Long addressId,AddressDto addressDto);


}
