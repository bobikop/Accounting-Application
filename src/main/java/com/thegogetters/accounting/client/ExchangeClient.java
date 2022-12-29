package com.thegogetters.accounting.client;

import com.thegogetters.accounting.dto.ExchangeRateDto;
import com.thegogetters.accounting.dto.Usd;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(url = "https://cdn.jsdelivr.net", name = "Exchange-Rate")
public interface ExchangeClient {

    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    ExchangeRateDto getUsdExchangeRate();



}
