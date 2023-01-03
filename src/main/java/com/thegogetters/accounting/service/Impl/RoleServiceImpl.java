package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.RoleDTO;
import com.thegogetters.accounting.entity.Role;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.RoleRepository;
import com.thegogetters.accounting.service.RoleService;
import com.thegogetters.accounting.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id), new RoleDTO());
    }

    @Override
    public List<RoleDTO> listRolesByLoggedUser() {

        // when logged-in as a root user show only Admin role (if no Admin user role) list root User role only
        if (securityService.getLoggedInUser().getRole().getDescription().equals("Root User")) {
            return roleRepository.findAll().stream()
                    .filter(role -> role.getDescription().equals("Admin"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());
        } else {
            return roleRepository.findAll().stream()
                    .filter(role -> !role.getDescription().equals("Root User"))
                    .map(role -> mapperUtil.convert(role, new RoleDTO()))
                    .collect(Collectors.toList());
        }
    }
}
