package com.thegogetters.accounting.service.Impl;


import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.entity.User;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.UserRepository;
import com.thegogetters.accounting.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //DI Services
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }


    // Method implementation

    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(UserDTO userDTO) {
        User user1 = mapperUtil.convert(userDTO, new User());
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);
    }

    @Override
    public void update(UserDTO userDTO) {
        User user1 = userRepository.findById(userDTO.getId()).get();
        User convertedUser = mapperUtil.convert(userDTO,new User());
        convertedUser.setId(user1.getId());
        userRepository.save(convertedUser);

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
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found"));
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public List<UserDTO> getAllUsersByLoggedInUser() {
        return null;
    }




}