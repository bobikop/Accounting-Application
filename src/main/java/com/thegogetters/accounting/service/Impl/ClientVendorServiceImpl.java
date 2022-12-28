package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ClientVendorRepository;
import com.thegogetters.accounting.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;


    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ClientVendorDto> findAllByClientVendorType(ClientVendorType clientVendorType) {

        List<ClientVendor> clientVendorList = clientVendorRepository.findAllByClientVendorType(clientVendorType);
        return clientVendorList.stream().
                map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findById(long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();

        return mapperUtil.convert(clientVendor,new ClientVendorDto());
    }






    /*
    @Override
    public List<ClientVendorDto> findAllByCompanyId(Long companyId) {

        List<ClientVendor> clientVendorList = clientVendorRepository.findAllByCompany_Id(companyId);

       return clientVendorList.stream().
                map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

     */
}
