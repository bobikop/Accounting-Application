package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.CategoryDto;
import com.thegogetters.accounting.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("newCategory", new CategoryDto());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("newCategory") CategoryDto categoryDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){

            model.addAttribute("newCategory", categoryDto);

            return "/category/category-create";
        }
        categoryService.createCategory(categoryDto);

        return "redirect:/categories/list";
    }

    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Model model){
        model.addAttribute("category", categoryService.checkAndSetProductStatus(id));
        return "/category/category-update";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryDto category, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){

            model.addAttribute("category", category);

            return "/category/category-update";
        }

        categoryService.updateCategory(category);

        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){

//        if (productService.checkAnyProductExist(id)){
//            redirectAttributes.addFlashAttribute("error","This category has products, so you cant delete...");
//            return "redirect:/categories/list";
//        }

        categoryService.deleteCategory(id);
        return "redirect:/categories/list";
    }
}
