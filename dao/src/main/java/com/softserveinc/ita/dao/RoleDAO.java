package com.softserveinc.ita.dao;


import com.softserveinc.ita.entity.Role;

import java.util.List;

public interface RoleDAO {

    String addRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(String roleId);

    void removeRoleById(String roleId);

}
