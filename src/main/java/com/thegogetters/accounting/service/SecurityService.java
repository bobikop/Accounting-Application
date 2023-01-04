package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.CompanyDto;
import com.thegogetters.accounting.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    UserDTO getLoggedInUser();
    CompanyDto getLoggedInCompany();
}
