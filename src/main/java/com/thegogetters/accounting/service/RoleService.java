package com.thegogetters.accounting.service;

import com.thegogetters.accounting.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id);
}
