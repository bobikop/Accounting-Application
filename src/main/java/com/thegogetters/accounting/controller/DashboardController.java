package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping
    public String retrieveDashboard(Model model){

        Map<String, Double> summaryNumbers = new HashMap<String, Double>();

        summaryNumbers.put("totalCost",1000.00);
        summaryNumbers.put("totalSales",1000.00);
        summaryNumbers.put("profitLoss",1000.00);

//        InvoiceDTO invoiceDTO = new InvoiceDTO("001", LocalDate.now(),new ClientVendorDto(),new BigDecimal(600),60,new BigDecimal(660));
//
//        List<InvoiceDTO> invoiceDTOS = new ArrayList<>();
//        invoiceDTOS.add(invoiceDTO);

        model.addAttribute("summaryNumbers", summaryNumbers);
        model.addAttribute("invoices",dashboardService.listLatestThreeApprovedInvoices());
        model.addAttribute("exchangeRates",dashboardService.listUsdExchangeRate());


        return "/dashboard";
    }


}
