package com.thegogetters.accounting.service.Impl;


import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.entity.User;
import com.thegogetters.accounting.entity.common.UserPrincipal;
import com.thegogetters.accounting.repository.UserRepository;
import com.thegogetters.accounting.service.SecurityService;
import com.thegogetters.accounting.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final UserService userService;

    public SecurityServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }

    @Override
    public UserDTO getLoggedInUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUserName(currentUsername);
    }


}
