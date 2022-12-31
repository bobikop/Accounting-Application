package com.thegogetters.accounting.service.impl;

import com.thegogetters.accounting.client.ExchangeClient;
import com.thegogetters.accounting.dto.ExchangeRate;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.enums.InvoiceStatus;
import com.thegogetters.accounting.service.DashboardService;
import com.thegogetters.accounting.service.InvoiceProductService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExchangeClient exchangeClient;

    private final InvoiceProductService invoiceProductService;

    public DashboardServiceImpl(ExchangeClient exchangeClient, InvoiceService invoiceService, InvoiceProductService invoiceProductService) {
        this.exchangeClient = exchangeClient;

        this.invoiceProductService = invoiceProductService;
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

    @Override
    public List<InvoiceDTO> listLatestThreeApprovedInvoices() {

   return      invoiceProductService.latestThreeTransactions().stream()
                .filter(invoiceDTO -> invoiceDTO.getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .sorted(Comparator.comparing(InvoiceDTO::getDate))
                .limit(3)
                .collect(Collectors.toList());




    }


}
