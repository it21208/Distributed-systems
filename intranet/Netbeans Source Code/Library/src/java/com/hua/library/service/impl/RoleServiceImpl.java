package com.hua.library.service.impl;

import com.hua.library.domain.Role;
import com.hua.library.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hua.library.domain.repository.RoleRepository;
import java.sql.SQLException;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    @Override
    public Role getRoleById(int id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public void addRole(Role role) {
        roleRepository.addRole(role);
    }

    @Override
    public void deleteRole(Role role) throws SQLException {
        roleRepository.deleteRole(role);
    }

    @Override
    public String roleOptionSet() {
        return roleRepository.roleOptionSet();
    }

    @Override
    public String roleOptionSet(int id) {
        return roleRepository.roleOptionSet(id);
    }

    @Override
    public void updateRoleServices(Role role) {
        roleRepository.updateRoleServices(role);
    }
    
}
