package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllCompanies(Model model){

        model.addAttribute("companies",companyService.listAll());

        return "/company/company-list";

    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("newCompany", new CompanyDto());

        return "/company/company-create";
    }

}
