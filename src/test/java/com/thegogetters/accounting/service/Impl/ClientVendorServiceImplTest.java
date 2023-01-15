package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.TestDocumentInitializer;
import com.thegogetters.accounting.entity.ClientVendor;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.enums.CompanyStatus;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.ClientVendorRepository;
import com.thegogetters.accounting.repository.InvoiceRepository;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.InvoiceService;
import com.thegogetters.accounting.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientVendorServiceImplTest {

    @Mock
    ClientVendorRepository clientVendorRepository;

    @Mock
    CompanyService companyService;

    @Mock
    InvoiceService invoiceService;
    @Mock
    InvoiceRepository invoiceRepository;

    @InjectMocks
    ClientVendorServiceImpl clientVendorService;

    @Spy
    MapperUtil mapperUtil = new MapperUtil(new ModelMapper());


    @Test
    void listAll_Test() {

        //given
        ClientVendorDto clientVendorDto = TestDocumentInitializer.getClientVendor(ClientVendorType.CLIENT);

        List<ClientVendor> clientVendorList = new ArrayList<>();
        clientVendorList.add(new ClientVendor());
        clientVendorList.add(new ClientVendor());
        clientVendorList.add(new ClientVendor());


        //when
        when(clientVendorRepository.findAll()).thenReturn(clientVendorList);

        clientVendorList.stream().map(clientVendor1 -> when(mapperUtil.convert(clientVendor1, new ClientVendorDto()))
                .thenReturn(clientVendorDto));


        //then // running real method
        List<ClientVendorDto> clientVendorDtoList = clientVendorService.listAll();

        assertTrue( clientVendorDtoList.size() == 3 );



    }

    @Test
    void update() {


    }

    @Test
    void deleteById() {
    }

    @Test
    void isClientVendorCanBeDeleted() {
    }

    @Test
    void findAllByCompany(){
        //given

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        Company company = mapperUtil.convert(companyDto,new Company());



        ClientVendorDto clientVendorDto = TestDocumentInitializer.getClientVendor(ClientVendorType.CLIENT);
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto,new ClientVendor());


        List<ClientVendor> clientVendorList = new ArrayList<>();
        clientVendorList.add(clientVendor);
        clientVendorList.add(clientVendor);
        clientVendorList.add(clientVendor);

        //when
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(companyDto);
        when(clientVendorRepository.findAllByCompany(any(Company.class))).thenReturn(clientVendorList);

        //then
        List<ClientVendorDto> allByCompany = clientVendorService.findAllByCompany(); // real method to test

        assertTrue(allByCompany.size() == 3);


    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void findAllByClientVendorTypeBelongsToCompany() {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        Company company = mapperUtil.convert(companyDto,new Company());



        ClientVendorDto clientVendorDto = TestDocumentInitializer.getClientVendor(ClientVendorType.CLIENT);
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto,new ClientVendor());

        /*
        List<ClientVendor> clientVendorList = new ArrayList<>();
        clientVendorList.add(clientVendor);
        clientVendorList.add(clientVendor);
        clientVendorList.add(clientVendor);
         */
        when(companyService.getCompanyOfLoggedInUser()).thenReturn(companyDto);


        when(clientVendorRepository.findAllByClientVendorTypeAndCompany(any(ClientVendorType.class),any(Company.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(clientVendor,clientVendor,clientVendor)));


        /*
        doReturn(new ArrayList<>(Arrays.asList(clientVendor,clientVendor,clientVendor)))
                .when(clientVendorRepository).findAllByClientVendorTypeAndCompany(any(ClientVendorType.class),any(Company.class));

         */
        List<ClientVendorDto> clientVendorDtoList = clientVendorService.findAllByClientVendorTypeBelongsToCompany(clientVendor.getClientVendorType());

        assertTrue(clientVendorDtoList.size() == 3);

    }
}