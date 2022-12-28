package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.dto.UserDTO;
import com.thegogetters.accounting.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<String, UserDTO> {

    private final UserService userService;

    public UserDtoConverter(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDTO convert(String source) {
        if (source == null || source.equals((""))){
            return null;
        }
        return userService.findByUserName(source);
    }
}
