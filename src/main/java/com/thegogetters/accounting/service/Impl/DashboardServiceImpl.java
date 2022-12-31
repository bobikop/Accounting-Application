package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.client.ExchangeClient;
import com.thegogetters.accounting.dto.*;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.enums.InvoiceType;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.service.DashboardService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExchangeClient exchangeClient;

    private final InvoiceService invoiceService;



    public DashboardServiceImpl(ExchangeClient exchangeClient, InvoiceService invoiceService, InvoiceProductService invoiceProductService, InvoiceService invoiceService1, InvoiceProductService invoiceProductService1, CompanyServiceImpl companyService, MapperUtil mapperUtil) {
        this.exchangeClient = exchangeClient;


        this.invoiceService = invoiceService1;

    }

    //----------------------------------------------------------------------------------///


    @Override
    public Map<String, Double> profitLoss() {
        return invoiceService.calculateCostSummary();
    }

    @Override
    public ExchangeRate listUsdExchangeRate() {

        ExchangeRate exchangeRate = new ExchangeRate();

        double euro = exchangeClient.getUsdExchangeRate().getUsd().getEur();
        exchangeRate.setEuro(euro);

        double gdb = exchangeClient.getUsdExchangeRate().getUsd().getGbp();
        exchangeRate.setBritishPound(gdb);

        double cad = exchangeClient.getUsdExchangeRate().getUsd().getCad();
        exchangeRate.setCanadianDollar(cad);

        double jpy = exchangeClient.getUsdExchangeRate().getUsd().getJpy();
        exchangeRate.setJapaneseYen(jpy);

        double inr = exchangeClient.getUsdExchangeRate().getUsd().getInr();
        exchangeRate.setIndianRupee(inr);


        return exchangeRate;

    }

    //---------------------------------------------------------------------------------//

    @Override
    public List<InvoiceDTO> listLatestThreeApprovedInvoices() {




        return invoiceService.lastThreeTransactions();


    }

    //-----------------------------------------------------------------------------------//



}
