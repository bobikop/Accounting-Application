package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.service.RoleService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("roles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());
        return "user/user-create";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("users", userService.listAllUsers());
            model.addAttribute("roles", roleService.listAllRoles());
            return "user/user-create";
        }
        userService.save(user);
        return "redirect:/user-create";
    }


    @GetMapping("/update/{username}")
    public String editUser (@PathVariable("username") String username, Model model){

        model.addAttribute("user", userService.findByUserName(username));
        model.addAttribute("roles", roleService.listAllRoles());
        return "user/user-update";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("users", userService.listAllUsers());
            model.addAttribute("roles", roleService.listAllRoles());
            return "user/user-update";
        }
        userService.update(userDTO);
        return "redirect:/user-list";
    }



    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){

        userService.deleteUser(username);
        return "redirect:/user-list";
    }





}


//GetMapping - list()

//GetMapping - create()
//PostMapping - create(InvoiceDto invoiceDto)

//GetMapping - update(Long invoiceId)
//PostMapping - update(Long invoiceId, InvoiceDto invoice)

//GetMapping - delete(Long invoiceId)