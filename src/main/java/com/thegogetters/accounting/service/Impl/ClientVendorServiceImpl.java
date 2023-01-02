package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ClientVendorRepository;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import feign.Client;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;


    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil,
                                   CompanyService companyService, @Lazy InvoiceService invoiceService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
    }

    @Override
    public List<ClientVendorDto> listAll() {

       List<ClientVendor> clientVendorList = clientVendorRepository.findAll();
        return clientVendorList.stream().
                map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void update(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorDto.getId()).orElseThrow();
        clientVendorDto.setClientVendorType(clientVendorDto
                .getClientVendorType() == null ? clientVendor.getClientVendorType() : clientVendorDto.getClientVendorType());
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(convertedClientVendor);
    }

    @Override
    public void deleteById(Long id) {

        boolean check = isClientVendorCanBeDeleted(id);
        if(check){
            ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
            clientVendor.setIsDeleted(true);
            clientVendor.setClientVendorName(clientVendor.getClientVendorName() + "-" + clientVendor.getId());
            clientVendorRepository.save(clientVendor);
        }
    }


//    public boolean isClientVendorCanBeDeleted(Long id){
//
//        List<InvoiceDTO> invoice = invoiceService.findAllByClientVendorId(id);
//
//        if(invoice.size() == 0){
//            return true;
//        }
//        return false;
//    }

    public boolean isClientVendorCanBeDeleted(Long id) {

        Long count = invoiceService.findAllByClientVendorId(id)
                .stream()
                .filter(invoiceDTO -> invoiceDTO.getInvoiceType().equals(InvoiceType.PURCHASE) || invoiceDTO.getInvoiceType()
                        .equals(InvoiceType.SALES)).count();

        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ClientVendorDto findById(long id) {
        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        clientVendorRepository.save(clientVendor);
    }

    @Override
    public List<ClientVendorDto> findAllByClientVendorTypeBelongsToCompany(ClientVendorType vendor) {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<ClientVendor> clientVendorList = clientVendorRepository.findAllByClientVendorTypeAndCompany(vendor, company);

        return clientVendorList.stream().
                map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());

    }

}
