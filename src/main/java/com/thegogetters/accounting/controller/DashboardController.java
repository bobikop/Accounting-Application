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
    public String retrieveDashboard(Model model) {

        model.addAttribute("summaryNumbers", dashboardService.profitLoss());
        model.addAttribute("invoices", dashboardService.listLatestThreeApprovedInvoices());
        model.addAttribute("exchangeRates", dashboardService.listUsdExchangeRate());


        return "/dashboard";
    }


}
