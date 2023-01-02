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

        if(securityService.getLoggedInUser().getRole().getId() == 1L){
            return listAllRoles().stream()
                    .filter(roleDTO -> roleDTO.getId() != 2L)
                    .collect(Collectors.toList());
        }

        if(securityService.getLoggedInUser().getRole().getId() == 2L){
            return listAllRoles().stream()
                    .filter(roleDTO -> roleDTO.getId() != 1L)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
