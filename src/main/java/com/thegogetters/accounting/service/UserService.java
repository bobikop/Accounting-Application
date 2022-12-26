package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findByUserName(String username);
    List<UserDTO> listAllUsers();
    void save(UserDTO user);
    void deleteByUserName(String username);
    UserDTO update(UserDTO user);
    List<UserDTO> listAllByRole(String role);

}
