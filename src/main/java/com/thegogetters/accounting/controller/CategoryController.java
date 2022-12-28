package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.service.CategoryService;
import com.thegogetters.accounting.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listCategories(Model model){
        model.addAttribute("categories", categoryService.listCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(CategoryDto categoryDto){
        categoryService.createCategory(categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, CategoryDto categoryDto){
        categoryService.updateCategory(id, categoryDto);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        if (productService.checkAnyProductExist(id)){
            redirectAttributes.addFlashAttribute("error","This category has products, so you cant delete...");
            return "redirect:/categories/list";
        }
        categoryService.deleteCategory(id);
        return "redirect:/categories/list";
    }
}
