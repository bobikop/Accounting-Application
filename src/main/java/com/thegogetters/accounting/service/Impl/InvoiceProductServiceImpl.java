package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.dto.*;
import com.thegogetters.accounting.entity.InvoiceProduct;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.InvoiceProductRepository;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;


    private final InvoiceService invoiceService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, CompanyService companyService, @Lazy InvoiceService invoiceService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
    }

    //----get All invoiceProducts----//

    List<InvoiceProduct> getAllInvoiceProducts(){

        return invoiceProductRepository.findAll();
    }


    @Override
    public List<InvoiceProductDTO> findInvoiceProductByInvoiceId(Long id) {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoice_Id(id);

        return invoiceProductList.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());

    }

    @Override
    public List<InvoiceProductDTO> findInvoiceProductByInvoiceId_for_productList(Long id) {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoice_Id(id);


        // for initializing the list for line 116 in the InvoicePurchaseController
        if (invoiceProductList.size() <= 0) {
            return new ArrayList<>();
        }


        List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductList.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());


        List<InvoiceProductDTO> collect = invoiceProductDTOS.stream().map(invoiceProductDTO -> {

            for (InvoiceProduct invoiceProduct : invoiceProductList) {


                //invoiceProductDTO.setQuantity(invoiceProduct.getQuantity());
                //invoiceProductDTO.setPrice(invoiceProduct.getPrice());
                //invoiceProductDTO.setTax(invoiceProduct.getTax());

                int totalTax = (invoiceProductDTO.getTax() * invoiceProductDTO.getPrice().intValueExact() * invoiceProductDTO.getQuantity()) / 100;

                int totalPrice = invoiceProductDTO.getPrice().intValueExact() * invoiceProductDTO.getQuantity();

                int total_withTax = (totalPrice + totalTax);

                invoiceProductDTO.setTotal(BigDecimal.valueOf(total_withTax));


                //invoiceProductDTO.setProduct(mapperUtil.convert(invoiceProduct.getProduct(), new ProductDTO()));
                invoiceProductDTO.setProduct(mapperUtil.convert(invoiceProductDTO.getProduct(), new ProductDTO()));
            }


            return invoiceProductDTO;

        }).collect(Collectors.toList());


        return collect;


    }


    @Override
    public InvoiceProductDTO save(Long invoiceId, InvoiceProductDTO invoiceProductDTO) {

        InvoiceDTO invoiceDTO = invoiceService.findInvoiceById(invoiceId);


        InvoiceProductDTO new_invoiceProductDTO = new InvoiceProductDTO();
        new_invoiceProductDTO.setInvoiceDto(invoiceDTO);

        new_invoiceProductDTO.setPrice(invoiceProductDTO.getPrice());
        new_invoiceProductDTO.setQuantity(invoiceProductDTO.getQuantity());
        new_invoiceProductDTO.setTax(invoiceProductDTO.getTax());
        new_invoiceProductDTO.setProfitLoss(invoiceProductDTO.getProfitLoss());
        new_invoiceProductDTO.setProduct(invoiceProductDTO.getProduct());


        InvoiceProduct invoiceProduct = mapperUtil.convert(new_invoiceProductDTO, new InvoiceProduct());

        invoiceProductRepository.save(invoiceProduct);


        return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());

    }


    @Override
    public void deleteById(Long invoiceProductId) { // for only invoiceProduct

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).orElseThrow();

        invoiceProduct.setIsDeleted(true);

        invoiceProductRepository.save(invoiceProduct);

    }

    @Override
    public void deleteByInvoiceId(Long invoiceId) { // for invoiceProduct list belongs to specific invoice

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoice_Id(invoiceId);

        invoiceProductList.forEach(invoiceProduct -> deleteById(invoiceProduct.getId()));

    }


}
