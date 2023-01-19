package com.thegogetters.accounting.controller;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.RoleDTO;
import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.service.CompanyService;
import com.thegogetters.accounting.service.RoleService;
import com.thegogetters.accounting.service.SecurityService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;
    private final SecurityService securityService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService, SecurityService securityService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
        this.securityService = securityService;
    }

    @GetMapping("/list")
    public String listAllUsers(Model model) throws AccountingAppException {
        model. addAttribute("users", userService.listAllUsersByLoggedInStatus());
        return "user/user-list";
    }


    @GetMapping("/create")
    public String createUser(Model model) throws AccountingAppException {

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
    public String editUser(@PathVariable ("id") Long id, RedirectAttributes redirectAttributes, Model model)  {

        UserDTO loggedInUser = securityService.getLoggedInUser();
        UserDTO userDTO = userService.findById(id);

//        if (userDTO.isOnlyAdmin()) {
//            String text = "This user is the only admin of" + userDTO.getCompany().getTitle() + "company. You can't change his/her company";
//            redirectAttributes.addFlashAttribute("text", text);
//            model.addAttribute("user", text);
//        }

        model.addAttribute("user", userDTO);
        model.addAttribute("companies", companyService.listAllByUser());
        model.addAttribute("userRoles", roleService.listRolesByLoggedUser());

        if (userDTO.getUsername().equals(loggedInUser.getUsername())) {
            model.addAttribute("userRoles", new RoleDTO(2L, "Admin"));
        }else{
            model.addAttribute("userRoles", roleService.listRolesByLoggedUser());
        }
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
