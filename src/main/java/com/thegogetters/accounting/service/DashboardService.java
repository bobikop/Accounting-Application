package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ExchangeRate;
import com.thegogetters.accounting.dto.InvoiceDTO;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String, Double> profitLoss();

    ExchangeRate listUsdExchangeRate();

    List<InvoiceDTO> listLatestThreeApprovedInvoices();
}
