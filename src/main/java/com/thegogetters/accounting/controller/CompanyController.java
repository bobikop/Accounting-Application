package com.thegogetters.accounting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @GetMapping("/list")
    public String listAllCompanies(){

        return "/company/company-list";

    }
}
