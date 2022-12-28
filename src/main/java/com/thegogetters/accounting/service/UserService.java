package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    void save(UserDTO user);
    UserDTO update(UserDTO user);
    UserDTO findByUserName(String username);
    void deleteUser(String username);


    CompanyDto findCompanyByUserName(String username);

}
