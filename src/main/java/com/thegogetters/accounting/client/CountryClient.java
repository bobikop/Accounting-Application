package com.thegogetters.accounting.client;


import com.thegogetters.accounting.dto.CountryDTO;
import com.thegogetters.accounting.dto.ExchangeRateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient(url = "https://www.universal-tutorial.com/api", name = "Country-FeignClient")
public interface CountryClient {


    @GetMapping("/getaccesstoken")
    public String getAccessToken(@RequestHeader(value = "api-token") String apiToken, @RequestHeader(value = "user-email") String userEmail);


    @GetMapping("/countries")
    List<CountryDTO> getCountries(@RequestHeader(value = "Authorization") String auth,
                      @RequestHeader(value = "Accept") String accept);


}
