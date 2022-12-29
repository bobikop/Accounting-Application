package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.dto.InvoiceProductDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.Invoice;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.InvoiceRepository;
import com.thegogetters.accounting.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;


    private final InvoiceProductService invoiceProductService;

    private final ClientVendorService clientVendorService;


    private final UserService userService;


    private final CompanyService companyService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, InvoiceProductService invoiceProductService, ClientVendorService clientVendorService, UserService userService, CompanyService companyService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.clientVendorService = clientVendorService;
        this.userService = userService;
        this.companyService = companyService;
    }


    //---------------------------------PURCHASE - SALES INVOICE LIST------------------------------------------------------------------//
    @Override
    public List<InvoiceDTO> findAllPurchaseInvoices() { // for purchase Invoice list page


        String username = SecurityContextHolder.getContext().getAuthentication().getName();//security give user who log in
        CompanyDto companyDto = userService.findCompanyByUserName(username);
        Company company = mapperUtil.convert(companyDto, new Company());




        //CompanyDto companyDto = companyService.getCompanyOfLoggedInUser(); it returns null
        ///Company company = mapperUtil.convert(companyDto, new Company());




        List<Invoice> invoiceList = invoiceRepository.findAllByCompanyAndInvoiceType(company, InvoiceType.PURCHASE);

        List<InvoiceDTO> invoiceDTOList = invoiceList.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());

        List<InvoiceDTO> collect = invoiceDTOList.stream().map(invoiceDTO -> {

            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());

            BigDecimal sum_total_price = BigDecimal.ZERO;
            int sum_total_tax = 0;
            int sum_total = 0;
            for (InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList) {

                BigDecimal totalPrice = invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()));
                sum_total_price = sum_total_price.add(totalPrice);
                invoiceDTO.setPrice(    sum_total_price         );

                int totalTax =  (invoiceProductDTO.getTax() * invoiceDTO.getPrice().intValueExact() ) / 100;
                sum_total_tax = totalTax;
                invoiceDTO.setTax(  sum_total_tax  );

                int totalPrice_withTax = invoiceDTO.getPrice().intValueExact() + invoiceDTO.getTax();
                sum_total = totalPrice_withTax;
                invoiceDTO.setTotal(BigDecimal.valueOf(sum_total) );


                invoiceDTO.setInvoiceProducts(invoiceProductDTOList);
            }


            return invoiceDTO;

        }).collect(Collectors.toList());



        return collect;








    }

    @Override
    public List<InvoiceDTO> findAllSalesInvoices() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();//security give user who log in
        CompanyDto companyDto = userService.findCompanyByUserName(username);
        Company company = mapperUtil.convert(companyDto, new Company());

        //CompanyDto companyDto = companyService.getCompanyOfLoggedInUser(); it returns null ??????????
        ///Company company = mapperUtil.convert(companyDto, new Company());


        List<Invoice>  invoices = invoiceRepository.findAllByCompanyAndInvoiceType(company, InvoiceType.SALES);


        List<InvoiceDTO> invoiceDTOList = invoices.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO()))
                .collect(Collectors.toList());


        List<InvoiceDTO> collect = invoiceDTOList.stream().map(invoiceDTO -> {

            List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());

            BigDecimal sum_total_price = BigDecimal.ZERO;
            int sum_total_tax = 0;
            int sum_total = 0;
            for (InvoiceProductDTO invoiceProductDTO : invoiceProductDTOList) {

                BigDecimal totalPrice = invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()));
                sum_total_price = sum_total_price.add(totalPrice);
                invoiceDTO.setPrice(    sum_total_price         );

                int totalTax =  (invoiceProductDTO.getTax() * invoiceDTO.getPrice().intValueExact() ) / 100;
                sum_total_tax = totalTax;
                invoiceDTO.setTax(  sum_total_tax  );

                int totalPrice_withTax = invoiceDTO.getPrice().intValueExact() + invoiceDTO.getTax();
                sum_total = totalPrice_withTax;
                invoiceDTO.setTotal(BigDecimal.valueOf(sum_total) );


                invoiceDTO.setInvoiceProducts(invoiceProductDTOList);
            }


            return invoiceDTO;

        }).collect(Collectors.toList());



        return collect;


    }


    //-----------------------------getNewInvoiceDTO Purchase - Sales ----------------------------------------------------//
    @Override
    public InvoiceDTO getNewPurchaseInvoiceDTO() {

        InvoiceDTO newInvoiceDTO = new InvoiceDTO();
        //newInvoiceDTO.setId(1L);
        newInvoiceDTO.setInvoiceNo("P-015");
        newInvoiceDTO.setDate(LocalDate.now());
        newInvoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        newInvoiceDTO.setInvoiceType(InvoiceType.PURCHASE);


        return newInvoiceDTO;
    }

    @Override
    public InvoiceDTO getNewSalesInvoiceDTO() {

        InvoiceDTO newInvoiceDTO = new InvoiceDTO();
        newInvoiceDTO.setInvoiceNo("S-015");
        newInvoiceDTO.setDate(LocalDate.now());
        newInvoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        newInvoiceDTO.setInvoiceType(InvoiceType.SALES);


        return newInvoiceDTO;
    }

    //----------------------------PURCHASE - SALES CREATE ----------------------------------------------------//

    @Override
    public InvoiceDTO create(InvoiceDTO invoiceDTO) {

        if (invoiceDTO.getInvoiceStatus() == null){
            invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        }

        if (invoiceDTO.getInvoiceType() == null){
            invoiceDTO.setInvoiceType(InvoiceType.PURCHASE);
        }



        if (invoiceDTO.getInvoiceProducts() == null){
            List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());
            invoiceDTO.setInvoiceProducts(invoiceProductDTOS);
        }


        String username = SecurityContextHolder.getContext().getAuthentication().getName();//security give user who log in

        CompanyDto companyDto = userService.findCompanyByUserName(username);

        invoiceDTO.setCompany(companyDto);

        Invoice convertInvoice = mapperUtil.convert(invoiceDTO, new Invoice());

        invoiceRepository.save(convertInvoice);


        return mapperUtil.convert(convertInvoice,new InvoiceDTO());


    }

    //----------------------------PURCHASE - SALES UPDATE ----------------------------------------------------//
    @Override
    public InvoiceDTO update(Long id , InvoiceDTO invoiceDTO) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        Invoice convertedInvoice = mapperUtil.convert(invoiceDTO, new Invoice());

        invoice.setInvoiceNo(convertedInvoice.getInvoiceNo());
        invoice.setClientVendor(convertedInvoice.getClientVendor());
        invoice.setDate(convertedInvoice.getDate());

        invoiceRepository.save(invoice);

        return mapperUtil.convert(invoice, new InvoiceDTO());

    }


    //----------------------------PURCHASE - SALES DELETE ----------------------------------------------------//
    @Override
    public void deleteById(Long id) {

        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        invoice.setIsDeleted(true);

        invoiceProductService.deleteByInvoiceId(id);

        invoiceRepository.save(invoice);


    }


    //----------------------------PURCHASE - SALES findInvoiceById ----------------------------------------------------//
    @Override
    public InvoiceDTO findInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();

        return mapperUtil.convert(invoice,new InvoiceDTO());

    }

    //----------------------------PURCHASE - SALES APPROVE ----------------------------------------------------//
    @Override
    public InvoiceDTO approveInvoice(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);

        invoiceRepository.save(invoice);

        return mapperUtil.convert(invoice,new InvoiceDTO());
    }
}
