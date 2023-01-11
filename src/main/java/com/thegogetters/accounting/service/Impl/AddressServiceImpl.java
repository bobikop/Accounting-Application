package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.client.CountryClient;
import com.thegogetters.accounting.dto.AddressDto;
import com.thegogetters.accounting.dto.CountryDTO;
import com.thegogetters.accounting.entity.Address;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.AddressRepository;
import com.thegogetters.accounting.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final CountryClient countryClient;


    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, CountryClient countryClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.countryClient = countryClient;
    }


    @Override
    public List<String> findAllCountries() {


        List<String> countriesNames = getCountriesNames();

        return countriesNames;

    }

    @Override
    public AddressDto update(Long addressId,AddressDto addressDto) {

        Address address = addressRepository.findById(addressId).orElseThrow();

        Address convertedAddress = mapperUtil.convert(addressDto, new Address());

        convertedAddress.setId(address.getId());

        addressRepository.save(convertedAddress);

        return mapperUtil.convert(convertedAddress,new AddressDto());

    }

    private String getAccessToken() {
        String accessToken = countryClient.getAccessToken("xh5jDDr3TocAy34Cy1yCL25BxJDLQJy00OmVLyWppSRUQtGpd71UrpTOeMkfAXhio3o",
                "can555@gmail.com");

        String[] split = accessToken.split(":");

        accessToken = split[1];

        accessToken=  accessToken.substring(1,accessToken.length()-2);
        return accessToken;
    }

    private List<String> getCountriesNames() {


        String accessToken = getAccessToken();

        List<CountryDTO> countries = countryClient.getCountries(
                "Bearer " + accessToken,
                "application/json");

        return countries.stream().map(CountryDTO::getCountryName).collect(Collectors.toList());
    }



}
