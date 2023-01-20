package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ClientVendorRepository;
import com.thegogetters.accounting.repository.InvoiceRepository;
import com.thegogetters.accounting.service.AddressService;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;


    //**************
    private final AddressService addressService;


    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil,
                                   CompanyService companyService, @Lazy InvoiceService invoiceService, InvoiceRepository invoiceRepository, AddressService addressService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
        this.invoiceRepository = invoiceRepository;
        this.addressService = addressService;
    }

    @Override
    public List<ClientVendorDto> listAll() {

       List<ClientVendor> clientVendorList = clientVendorRepository.findAll();
        return clientVendorList.stream().
                map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void update(ClientVendorDto clientVendorDto) throws AccountingAppException {
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorDto.getId())
                .orElseThrow(()-> new AccountingAppException("ClientVendor not found"));

        clientVendor.setClientVendorType(clientVendorDto.getClientVendorType());

        //***********************************
        addressService.update(clientVendor.getAddress().getId(), clientVendorDto.getAddress());
        //***********************************

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        Company company = mapperUtil.convert(companyDto, new Company());
        ClientVendor convert = mapperUtil.convert(clientVendorDto, new ClientVendor());

        convert.setId(clientVendorDto.getId());
        convert.setCompany(company);

        clientVendorRepository.save(convert);
    }

    @Override
    public void deleteById(Long id) throws AccountingAppException {

        boolean check = isClientVendorCanBeDeleted(id);
        if(!check){
            ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow(()-> new AccountingAppException("ClientVendor not found"));
            clientVendor.setIsDeleted(true);

            clientVendor.setClientVendorName(clientVendor.getClientVendorName() + "-" + clientVendor.getId());
            clientVendorRepository.save(clientVendor);
        }
    }

    public boolean isClientVendorCanBeDeleted(Long id){

        List<InvoiceDTO> invoice = invoiceService.findAllByClientVendorId(id);

        if(invoice.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public List<ClientVendorDto> findAllByCompany() {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<ClientVendor> clientVendorList = clientVendorRepository.findAllByCompany(company);
        return clientVendorList.stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType))
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto()))
                .collect(Collectors.toList());
    }


    @Override
    public ClientVendorDto findById(long id) throws AccountingAppException {
        ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(()-> new AccountingAppException("ClientVendor not found"));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto, new ClientVendor());
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());
        clientVendor.setCompany(company);

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
