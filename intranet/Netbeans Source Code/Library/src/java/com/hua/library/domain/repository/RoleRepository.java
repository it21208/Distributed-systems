package com.hua.library.domain.repository;

import com.hua.library.domain.Role;
import java.sql.SQLException;
import java.util.List;

public interface RoleRepository {
    List<Role> getAllRoles();
    public Role getRoleById(int id);
    Role getRoleByName(String name);
    void addRole(Role role);  
    void deleteRole(Role role) throws SQLException;
    void updateRoleServices(Role role);
    String roleOptionSet();
    String roleOptionSet(int id);      
}
