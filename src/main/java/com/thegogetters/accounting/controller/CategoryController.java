package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listCategories(Model model){
        model.addAttribute("categories", categoryService.listCategories());
        return "/category/category-list";
    }

    //GetMapping - createInvoice()
    //PostMapping - createInvoice(InvoiceDto invoiceDto)

    //GetMapping - updateInvoice(Long invoiceId)
    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "/category/category-update";
    }
    //PostMapping - updateInvoice(Long invoiceId, InvoiceDto invoice)

    //GetMapping - deleteInvoice(Long invoiceId)

    //
}
