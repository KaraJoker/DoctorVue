package com.chero.client.service.impl;

import com.chero.client.dao.SystemRoleRepository;
import com.chero.client.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl implements SysRoleService{

    @Autowired
    SystemRoleRepository sysRoleRepository;

    @Override
    public String findRoleByUserId(Integer id) {
        return sysRoleRepository.findSystemRoleByRoleId(id);
    }

    @Override
    public void saveUserRole(Integer id) {
        sysRoleRepository.saveSystemRole(id);
    }
}
