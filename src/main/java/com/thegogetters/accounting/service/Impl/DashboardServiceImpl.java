package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.client.ExchangeClient;
import com.thegogetters.accounting.dto.ExchangeRate;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.service.DashboardService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExchangeClient exchangeClient;

    private final InvoiceService invoiceService;


    public DashboardServiceImpl(ExchangeClient exchangeClient, InvoiceService invoiceService, InvoiceProductService invoiceProductService, InvoiceService invoiceService1) {
        this.exchangeClient = exchangeClient;


        this.invoiceService = invoiceService1;
    }

    //----------------------------------------------------------------------------------///


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
