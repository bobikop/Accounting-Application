package com.thegogetters.accounting.service.Impl;


import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.entity.Company;
import com.thegogetters.accounting.entity.User;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.UserRepository;
import com.thegogetters.accounting.service.SecurityService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //DI Services
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, PasswordEncoder passwordEncoder, @Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }


/*    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> mapperUtil.convert(user, new UserDTO()))
                .collect(Collectors.toList());
    }
*/

    @Override
    public void save(UserDTO userDTO) {
        User user1 = mapperUtil.convert(userDTO, new User());
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);
    }

    @Override
    public void update(UserDTO userDTO) {
        User user1 = userRepository.findById(userDTO.getId()).get();
        User convertedUser = mapperUtil.convert(userDTO, new User());
        convertedUser.setId(user1.getId());
        userRepository.save(convertedUser);

    }

    @Override
    public UserDTO findByUserName(String username) {
        return mapperUtil.convert(userRepository.findByUsername(username), new UserDTO());
    }

    @Override
    public UserDTO findById(Long id) throws AccountingAppException {
        User user = userRepository.findById(id).orElseThrow(() -> new AccountingAppException("User not found"));
        return mapperUtil.convert(user, new UserDTO());
    }

    @Override
    public List<UserDTO> listAllUsersByLoggedInStatus() throws AccountingAppException {

        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {

            return userRepository.findAllByRoleDescriptionOrderByCompanyTitle("Admin").stream()
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(userDto -> userDto.setOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        } else {

            Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

            return userRepository.findAllByCompanyOrderByRoleDescription(company).stream()
                    .map(user -> mapperUtil.convert(user, new UserDTO()))
                    .peek(userDto -> userDto.setOnlyAdmin(isOnlyAdmin(userDto)))
                    .collect(Collectors.toList());
        }
    }
    @Override
    public void deleteById(Long id) throws AccountingAppException {
        User user = userRepository.findById(id).orElseThrow(() -> new AccountingAppException("User not found"));
        user.setIsDeleted(true);
        user.setUsername(user.getUsername() + "-" + user.getId());
        userRepository.save(user);
    }

    @Override
    public boolean usernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isOnlyAdmin(UserDTO userDTO){

        Company company = mapperUtil.convert(userDTO.getCompany(), new Company());
        List<User> admins = userRepository.findAllByRoleDescriptionAndCompanyOrderByCompanyTitleAscRoleDescription("Admin",company);
        return userDTO.getRole().getDescription().equals("Admin") && admins.size() == 1;
    }

}