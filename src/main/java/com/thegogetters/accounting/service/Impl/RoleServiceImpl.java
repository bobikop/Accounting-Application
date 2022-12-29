package com.thegogetters.accounting.service.Impl;

import com.thegogetters.accounting.dto.RoleDTO;
import com.thegogetters.accounting.entity.Role;
import com.thegogetters.accounting.mapper.MapperUtil;
import com.thegogetters.accounting.repository.RoleRepository;
import com.thegogetters.accounting.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
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

}
