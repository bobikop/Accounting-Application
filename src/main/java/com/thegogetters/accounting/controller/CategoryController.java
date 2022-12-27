package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    //GetMapping - listInvoices()
    @GetMapping("/list")
    public String listCategories(Model model){
        model.addAttribute("categories", categoryService.listCategories());
        return "/category/category-list";
    }

    //GetMapping - createInvoice()
    //PostMapping - createInvoice(InvoiceDto invoiceDto)

    //GetMapping - updateInvoice(Long invoiceId)
    //PostMapping - updateInvoice(Long invoiceId, InvoiceDto invoice)

    //GetMapping - deleteInvoice(Long invoiceId)

    //
}
