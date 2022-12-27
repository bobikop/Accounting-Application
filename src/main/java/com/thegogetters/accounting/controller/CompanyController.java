package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.service.CompanyService;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    //=========================================================================//
    @GetMapping("/list")
    public String listAllCompanies(Model model) {

        model.addAttribute("companies", companyService.listAll());

        return "/company/company-list";

    }

    //=========================================================================//

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("newCompany", new CompanyDto());

        return "/company/company-create";
    }

    //=========================================================================//

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("newCompany") CompanyDto companyDto, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {

            model.addAttribute("newCompany", companyDto);

            return "/company/company-create";

        }

        companyService.save(companyDto);

        return "redirect:/companies/list";
    }

    //=========================================================================//

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") String id, Model model) {


        model.addAttribute("company", companyService.findById(Long.parseLong(id)));

        return "/company/company-update";
    }

    //=========================================================================//

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("company") CompanyDto companyDto) {

        companyService.update(companyDto);

        return "redirect:/companies/list";
    }

    //=========================================================================//


    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") String id) {

        companyService.changeCompanyStatusById(Long.parseLong(id));

        return "redirect:/companies/list";
    }

    //=========================================================================//

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") String id) {

        companyService.changeCompanyStatusById(Long.parseLong(id));

        return "redirect:/companies/list";
    }


}
