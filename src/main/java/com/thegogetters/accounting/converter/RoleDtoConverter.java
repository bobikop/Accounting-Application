package com.thegogetters.accounting.converter;

import com.thegogetters.accounting.custom.exception.AccountingAppException;
import com.thegogetters.accounting.dto.RoleDTO;
import com.thegogetters.accounting.service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDTO> {


    private final RoleService roleService;

    public RoleDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public RoleDTO convert(String source) {
        if(source == null || source.equals("")){
            return null;
        }
        try {
            return roleService.findById(Long.parseLong(source)); // method parses the string argument s as a signed decimal long
        } catch (AccountingAppException e) {
            throw new RuntimeException(e);
        }
    }
}
