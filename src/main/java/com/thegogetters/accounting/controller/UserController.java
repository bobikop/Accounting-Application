package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.service.RoleService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }



    @GetMapping("/list")
    public String listAllUsers(Model model){

        model. addAttribute("users", userService.listAllUsers());

        return "user/user-list";
    }

}


//GetMapping - list()

//GetMapping - create()
//PostMapping - create(InvoiceDto invoiceDto)

//GetMapping - update(Long invoiceId)
//PostMapping - update(Long invoiceId, InvoiceDto invoice)

//GetMapping - delete(Long invoiceId)