package com.thegogetters.accounting.service.Impl;


import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {




    @Override
    public List<UserDTO> listAllUsers() {
        return null;
    }

    @Override
    public void save(UserDTO user) {

    }

    @Override
    public UserDTO update(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO findByUserName(String username) {
        return null;
    }
}
