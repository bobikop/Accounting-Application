package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.ClientVendorDto;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.service.ClientVendorService;
import com.thegogetters.accounting.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final InvoiceService invoiceService;

    public ClientVendorController(ClientVendorService clientVendorService, InvoiceService invoiceService) {
        this.clientVendorService = clientVendorService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/list")
    public String listAll(Model model) {
        model.addAttribute("clientVendors", clientVendorService.findAllByCompany());
        return "clientVendor/clientVendor-list";
    }
    //*******************************************************************************************

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("newClientVendor", new ClientVendorDto());
        return "clientVendor/clientVendor-create";
    }

    //*******************************************************************************************

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("newClientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
            return "clientVendor/clientVendor-create";
        }

        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

    //**************************************************************************************

    @GetMapping("/update/{id}")
    public String edit(@PathVariable("id") Long id, Model model) throws AccountingAppException {
        model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));
        model.addAttribute("clientVendor", clientVendorService.findById(id));
        return "clientVendor/clientVendor-update";
    }

    //***************************************************************************************


    @PostMapping("/update/{id}")
    public String update(/*@Valid */@ModelAttribute("clientVendor") ClientVendorDto clientVendorDto, BindingResult bindingResult, Model model) throws AccountingAppException {

        if(bindingResult.hasErrors()){
            model.addAttribute("clientVendor", clientVendorDto);
            model.addAttribute("clientVendorTypes", Arrays.asList(ClientVendorType.values()));

            return "clientVendor/clientVendor-update";
        }

        clientVendorService.update(clientVendorDto);
        return "redirect:/clientVendors/list";
    }


    //****************************************************************************************

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws AccountingAppException {
        if(clientVendorService.isClientVendorCanBeDeleted(id)){
            redirectAttributes.addFlashAttribute("error", "Can not be deleted..." +
                    "You have invoices with this client/vendor");
            return "redirect:/clientVendors/list";
        }
        clientVendorService.deleteById(id);
        return "redirect:/clientVendors/list";
    }

    //***************************************************************************************



}
