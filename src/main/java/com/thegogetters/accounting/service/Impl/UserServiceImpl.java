package com.thegogetters.accounting.service.Impl;


import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.User;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.UserRepository;
import com.thegogetters.accounting.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
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
        return mapperUtil.convert(userRepository.findByUsername(username), new UserDTO());
    }


    @Override
    public void deleteUser(String username) {

        User user = userRepository.findByUsername(username);

        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId());

        userRepository.save(user);

    }



    @Override
    public CompanyDto findCompanyByUserName(String username) {
        User username1 = userRepository.findByUsername(username);
        Company company = username1.getCompany();
        return mapperUtil.convert(company,new CompanyDto());
    }

}