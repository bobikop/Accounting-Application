package com.thegogetters.accounting.client;

import com.thegogetters.accounting.dto.ExchangeRateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net")
public interface ExchangeClient {

    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    ExchangeRateDto getUsdExchangeRate();

}
