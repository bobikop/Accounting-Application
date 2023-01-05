package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.enums.ClientVendorType;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.RoleService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

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

 /*   @ModelAttribute
    public void commonAttributes(Model model){
    }*/


    @GetMapping("/list")
    public String listAllUsers(Model model){
        model. addAttribute("users", userService.listAllUsersByLoggedInStatus());
        return "user/user-list";
    }


    @GetMapping("/create")
    public String createUser(Model model){

        model.addAttribute("newUser", new UserDTO());
        model.addAttribute("userRoles", roleService.listRolesByLoggedUser());
        model.addAttribute("users", userService.listAllUsersByLoggedInStatus());
        model.addAttribute("companies", companyService.listAllByUser());
        return "user/user-create";
    }


    @PostMapping("/create")
    public String saveUser(@Valid @ModelAttribute("newUser") UserDTO user, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors() || userService.usernameExist(user.getUsername())) {
            if (userService.usernameExist(user.getUsername())) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }
            model.addAttribute("userRoles", roleService.listRolesByLoggedUser());
            model.addAttribute("companies", companyService.listAllByUser());
            return "user/user-create";
        }
        userService.save(user);
        return "redirect:/users/list";
    }


    @GetMapping("/update/{id}")
    public String editUser(@PathVariable ("id") Long id, Model model) throws AccountingAppException {

        model.addAttribute("user",userService.findById(id));
        model.addAttribute("userRoles", roleService.listRolesByLoggedUser());
        model.addAttribute("companies", companyService.listAllByUser());

        return "user/user-update";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@Valid @ModelAttribute ("user") UserDTO userDTO, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("userRoles", roleService.listRolesByLoggedUser());
            model.addAttribute("companies", companyService.listAllByUser());
            return "user/user-update";
        }
        userService.update(userDTO);
        return "redirect:/users/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) throws AccountingAppException {

        userService.deleteById(id);
        return "redirect:/users/list";
    }
}
