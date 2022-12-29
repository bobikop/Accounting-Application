package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.service.CompanyService;
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
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }


    @GetMapping("/list")
    public String listAllUsers(Model model){
        model. addAttribute("users", userService.listAllUsers());
        return "user/user-list";
    }


    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());
        model.addAttribute("companies", companyService.listAll());
        return "user/user-create";
    }


    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("users", userService.listAllUsers());
            model.addAttribute("userRoles", roleService.listAllRoles());
            model.addAttribute("companies", companyService.listAll());
            return "user/user-create";
        }
        userService.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/update/{id}")
    public String editUser(@PathVariable ("id") Long id, Model model){
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", companyService.listAll());

        return "user/user-update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute ("user") UserDTO userDTO){
        userService.update(userDTO);
        return "redirect:/users/list";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);

        return "redirect:/user-list";
    }


}
