package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.ExchangeRate;
import com.thegogetters.accounting.dto.InvoiceDTO;

import java.util.List;

public interface DashboardService {

    ExchangeRate listUsdExchangeRate();

    List<InvoiceDTO> listLatestThreeApprovedInvoices();
}
