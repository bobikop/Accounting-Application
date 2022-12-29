package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();
    void save(UserDTO userDTO);
    void update(UserDTO userDTO);
    UserDTO findByUserName(String username);
    void deleteUser(String username);
    UserDTO findById(Long id);


/*
    Need to separate listed users depending on who is looged in in to application
            Case 1: root user is looged in = show all the companies -users -Admins only
            Case 2: admin is logged in = show managers, admins, employees

*/


    List<UserDTO> getAllUsersByLoggedInUser();


}
