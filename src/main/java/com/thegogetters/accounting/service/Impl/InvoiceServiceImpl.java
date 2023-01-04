package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.*;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.Invoice;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.InvoiceRepository;
import com.thegogetters.accounting.service.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final ProductService productService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil,
                              InvoiceProductService invoiceProductService,
                              CompanyService companyService, ProductService productService) {

        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.companyService = companyService;
        this.productService = productService;
    }




    //-----------------------------------DASHBOARD---------------------


    @Override
    public List<InvoiceDTO> lastThreeTransactions() {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        return invoiceProductService.FindAllInvoiceProducts().stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getId().equals(companyDto.getId()))
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .map(invoiceProduct -> {
                    BigDecimal tax = BigDecimal.valueOf(invoiceProduct.getTax());

                    InvoiceDTO invoiceDTO = new InvoiceDTO();
                    invoiceDTO.setInvoiceNo(invoiceProduct.getInvoice().getInvoiceNo());
                    invoiceDTO.setClientVendor(mapperUtil.convert(invoiceProduct.getInvoice().getClientVendor(), new ClientVendorDto()));
                    invoiceDTO.setDate(invoiceProduct.getInvoice().getDate());
                    invoiceDTO.setPrice(invoiceProduct.getPrice());
                    invoiceDTO.setTax(invoiceProduct.getTax());
                    invoiceDTO.setTotal(invoiceProduct.getPrice().multiply(tax.divide(BigDecimal.valueOf(100))).add(invoiceProduct.getPrice()));
                    return invoiceDTO;
                })
                .sorted(comparing(InvoiceDTO::getDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

    }


    @Override
    public List<InvoiceDTO> findAllApprovedInvoicesBelongsToCompany(InvoiceStatus invoiceStatus, InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Invoice> invoiceList = invoiceRepository.findAllByCompanyAndInvoiceStatusAndInvoiceType(company,invoiceStatus,invoiceType)
                .stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceNo))
                .collect(Collectors.toList());


        return calculateInvoiceDetails(invoiceList);



    }

    @Override
    public Map<String, Double> calculateCostSummary() {

        List<InvoiceDTO> allApprovedPurchaseInvoicesBelongsToCompany = findAllApprovedInvoicesBelongsToCompany(InvoiceStatus.APPROVED,InvoiceType.PURCHASE);

        List<InvoiceDTO> allApprovedSalesInvoicesBelongsToCompany = findAllApprovedInvoicesBelongsToCompany(InvoiceStatus.APPROVED,InvoiceType.SALES);

        double totalCost = allApprovedPurchaseInvoicesBelongsToCompany.stream().map(InvoiceDTO::getTotal).mapToDouble(BigDecimal::doubleValue).sum(); // company : 2, total cost : 2750

        double totalSales = allApprovedSalesInvoicesBelongsToCompany.stream().map(InvoiceDTO::getTotal).mapToDouble(BigDecimal::doubleValue).sum();// company : 2, total sales  : 660
        // profitLost
        double profitLoss = allApprovedSalesInvoicesBelongsToCompany.stream()
                .flatMap(invoiceDTO -> invoiceDTO.getInvoiceProducts().stream())
                .map(InvoiceProductDTO::getProfitLoss)
                .mapToDouble(BigDecimal::doubleValue)
                .sum(); //110

        Map<String, Double> costSummary = new HashMap<>();

        costSummary.put("totalCost", totalCost);
        costSummary.put("totalSales", totalSales);
        costSummary.put("profitLoss", profitLoss);


        return costSummary;
    }







    //---------------------------------PURCHASE - SALES INVOICE LIST------------------------------------------------------------------//



    @Override
    public List<InvoiceDTO> findAllInvoicesBelongsToCompany(InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Invoice> invoiceList = invoiceRepository.findAllByCompanyAndInvoiceType(company, invoiceType)
                .stream().sorted(comparing(Invoice::getInvoiceNo))
                .collect(Collectors.toList());


        return calculateInvoiceDetails(invoiceList);
    }


    private List<InvoiceDTO> calculateInvoiceDetails(List<Invoice> invoiceList) {
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
                invoiceDTO.setPrice(sum_total_price);

                int totalTax = (invoiceProductDTO.getTax() * invoiceDTO.getPrice().intValueExact()) / 100;
                sum_total_tax = totalTax;
                invoiceDTO.setTax(sum_total_tax);

                int totalPrice_withTax = invoiceDTO.getPrice().intValueExact() + invoiceDTO.getTax();
                sum_total = totalPrice_withTax;
                invoiceDTO.setTotal(BigDecimal.valueOf(sum_total));


            }

            invoiceDTO.setInvoiceProducts(invoiceProductDTOList);

            return invoiceDTO;

        }).collect(Collectors.toList());


        return collect;
    }

    //-----------------------------getNewInvoiceDTO Purchase - Sales ----------------------------------------------------//

    public InvoiceDTO getNewInvoiceDTO(InvoiceType invoiceType) {

        InvoiceDTO newInvoiceDTO = new InvoiceDTO();

        newInvoiceDTO.setInvoiceNo(getInvoiceNo(invoiceType));


        if (invoiceType.getValue().equals("Purchase")) {

            //newInvoiceDTO.setInvoiceNo("P-015");

            newInvoiceDTO.setInvoiceType(InvoiceType.PURCHASE);
        } else {
            //newInvoiceDTO.setInvoiceNo("S-015");
            newInvoiceDTO.setInvoiceType(InvoiceType.SALES);
        }

        newInvoiceDTO.setDate(LocalDate.now());
        newInvoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);


        return newInvoiceDTO;


    }

    private String getInvoiceNo(InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        Company company = mapperUtil.convert(companyDto, new Company());

        Invoice invoice = invoiceRepository.findAllByCompanyAndInvoiceType(company, invoiceType).stream()
                .sorted(comparing(Invoice::getInvoiceNo).reversed())
                .findAny().get();

        String invoiceNo = invoice.getInvoiceNo(); // P-002

        int i = invoiceNo.length();
        String s = invoiceNo.substring(0, i - 1) + (Integer.parseInt(invoiceNo.substring(i - 1)) + 1); // P-003


        return s;

    }


    //----------------------------PURCHASE - SALES CREATE ----------------------------------------------------//

    @Override
    public InvoiceDTO create(InvoiceType invoiceType, InvoiceDTO invoiceDTO) {

        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);

        if (invoiceDTO.getInvoiceProducts() == null) {
            List<InvoiceProductDTO> invoiceProductDTOS = invoiceProductService.findInvoiceProductByInvoiceId(invoiceDTO.getId());
            invoiceDTO.setInvoiceProducts(invoiceProductDTOS);
        }


        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        invoiceDTO.setCompany(companyDto);

        Invoice convertInvoice = mapperUtil.convert(invoiceDTO, new Invoice());

        invoiceRepository.save(convertInvoice);

        return mapperUtil.convert(convertInvoice, new InvoiceDTO());

    }

    //----------------------------PURCHASE - SALES UPDATE ----------------------------------------------------//
    @Override
    public InvoiceDTO update(Long id, InvoiceDTO invoiceDTO) {

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

        List<InvoiceDTO> invoiceDTOS = calculateInvoiceDetails(Arrays.asList(invoice));

        InvoiceDTO invoiceDTO = invoiceDTOS.get(0);

        return invoiceDTO;

    }

    //----------------------------PURCHASE - SALES APPROVE ----------------------------------------------------//
    @Override
    public InvoiceDTO approveInvoice(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow();

        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoice.setDate(LocalDate.now());
        invoiceRepository.save(invoice);


        updateQuantityOfProductAfterApproved(invoice.getInvoiceType(), invoiceId);

        calculateProfitLoss(invoiceId, invoice);

        return mapperUtil.convert(invoice, new InvoiceDTO());
    }


    private void updateQuantityOfProductAfterApproved(InvoiceType invoiceType,Long invoiceId) {

        List<InvoiceProductDTO> invoiceProductDTOList = invoiceProductService.findInvoiceProductByInvoiceId(invoiceId);

        invoiceProductDTOList.stream().map(invoiceProductDTO -> {

            ProductDTO productDTO = productService.getProductById(invoiceProductDTO.getProduct().getId());
            Integer quantityOfInvoiceProduct = invoiceProductDTO.getQuantity();
            Integer quantityInStockOfProduct = productDTO.getQuantityInStock();
            if (invoiceType.getValue().equals("Purchase")){
                Integer increasedTotalQuantityInStock = quantityInStockOfProduct + quantityOfInvoiceProduct;
                productDTO.setQuantityInStock(increasedTotalQuantityInStock);
                invoiceProductDTO.setRemainingQuantity(increasedTotalQuantityInStock);
            }else{
                if (quantityOfInvoiceProduct <= quantityInStockOfProduct){
                    Integer decreasedTotalQuantityInStock = quantityInStockOfProduct - quantityOfInvoiceProduct;
                    productDTO.setQuantityInStock(decreasedTotalQuantityInStock);
                    invoiceProductDTO.setRemainingQuantity(decreasedTotalQuantityInStock);
                }else {
                    throw new RuntimeException("Quantity of " + productDTO.getName() + " is not enough to sell : " + (quantityInStockOfProduct - quantityOfInvoiceProduct) ) ;
                }
            }

            productService.update(productDTO);
            invoiceProductService.save(invoiceId,invoiceProductDTO);

            return invoiceProductDTO;

        }).collect(Collectors.toList());


    }

    private void calculateProfitLoss(Long invoiceId, Invoice invoice) {
        List<InvoiceProductDTO> productList = invoiceProductService.findInvoiceProductByInvoiceId_for_productList(invoiceId);

        productList.stream().map(invoiceProductDTO -> {

            Integer quantity = invoiceProductDTO.getQuantity();//5

            BigDecimal total = invoiceProductDTO.getTotal();//1650

            Integer totalQuantity = invoiceRepository.retrieveTotalQuantityForPurchaseInvoices(invoice.getCompany().getId(),invoiceProductDTO.getProduct().getId());
            Integer totalPrice = invoiceRepository.retrieveTotalPriceForPurchaseInvoices(invoice.getCompany().getId(),invoiceProductDTO.getProduct().getId());

            if (totalQuantity == null) totalQuantity = 1;
            if (totalPrice == null) totalPrice = 0;

            BigDecimal multiply = total.multiply(BigDecimal.valueOf(totalQuantity)); // 1650 * 10

            BigDecimal totalProfit_would_be_if_I_sell_purchase_quantity = multiply.divide(BigDecimal.valueOf(quantity)); //  (1650 * 10 ) / 5 : 3300

            double totalProfit_for_purchase_quantity = totalProfit_would_be_if_I_sell_purchase_quantity.doubleValue() - totalPrice; // 3300 - 2750 : 550

            double profit_for_one_quantity = totalProfit_for_purchase_quantity / totalQuantity; // 550 / 10 : 55

            double profit_for_sales_quantity = quantity * profit_for_one_quantity; // 5 * 55 : 275

            if(invoice.getInvoiceType().getValue().equals("Sales")) {

                invoiceProductDTO.setProfitLoss(BigDecimal.valueOf(profit_for_sales_quantity));

            }else{
                invoiceProductDTO.setProfitLoss(BigDecimal.valueOf(0));
            }

            invoiceProductService.save(invoiceId, invoiceProductDTO);

            return invoiceProductDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findAllByClientVendorId(Long id) {

        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser(); //it returns null,
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Invoice> invoiceList = invoiceRepository.findAllByCompanyAndClientVendor_IdAndInvoiceStatus(company, id, InvoiceStatus.APPROVED);

        return invoiceList.stream().map(invoice -> mapperUtil.convert(invoice, new InvoiceDTO())).collect(Collectors.toList());

    }

}
