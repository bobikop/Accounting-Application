package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.service.AddressService;
import com.thegogetters.accounting.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final AddressService addressService;

    public CompanyController(CompanyService companyService, AddressService addressService) {
        this.companyService = companyService;
        this.addressService = addressService;
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

        List<String> countryNames = addressService.findAllCountries();
        model.addAttribute("countries", countryNames);

        return "/company/company-create";
    }

    //=========================================================================//

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("newCompany") CompanyDto companyDto, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {

            model.addAttribute("newCompany", companyDto);

            List<String> countryNames = addressService.findAllCountries();
            model.addAttribute("countries", countryNames);

            return "/company/company-create";

        }

        companyService.save(companyDto);

        return "redirect:/companies/list";
    }

    //=========================================================================//

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") String id, Model model) throws AccountingAppException {


        model.addAttribute("company", companyService.findById(Long.parseLong(id)));

        List<String> countryNames = addressService.findAllCountries();
        model.addAttribute("countries", countryNames);

        return "/company/company-update";
    }

    //=========================================================================//

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("company") CompanyDto companyDto, BindingResult bindingResult, Model model) throws AccountingAppException {

        if (bindingResult.hasErrors()) {

            model.addAttribute("company", companyDto);

            return "/company/company-update";

        }

        companyService.update(companyDto);

        return "redirect:/companies/list";
    }

    //=========================================================================//


    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") String id) throws AccountingAppException {

        companyService.changeCompanyStatusById(Long.parseLong(id));

        return "redirect:/companies/list";
    }

    //=========================================================================//

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") String id) throws AccountingAppException {

        companyService.changeCompanyStatusById(Long.parseLong(id));

        return "redirect:/companies/list";
    }


}
