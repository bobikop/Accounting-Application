package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.client.ExchangeClient;
import com.thegogetters.accounting.dto.ExchangeRate;
import com.thegogetters.accounting.service.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExchangeClient exchangeClient;

    public DashboardServiceImpl(ExchangeClient exchangeClient) {
        this.exchangeClient = exchangeClient;
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
}
