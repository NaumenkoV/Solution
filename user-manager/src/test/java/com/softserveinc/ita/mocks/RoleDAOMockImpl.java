package com.softserveinc.ita.mocks;

import com.softserveinc.ita.dao.RoleDAO;
import com.softserveinc.ita.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RoleDAOMockImpl implements RoleDAO {

    private AtomicInteger idAutoGeneration = new AtomicInteger();
    private Hashtable<String, Role> dbDaoMock = new Hashtable<String, Role>();

    public RoleDAOMockImpl(){
        Role role = new Role("Admin");
        role.setId("1");
        dbDaoMock.put("1",role);
        role = new Role("HR");
        role.setId("2");
        dbDaoMock.put("2",role);
    }

    @Override
    public String addRole(Role role) {
        return null;
    }

    public List<Role> getAllRoles(){
        List<Role> roles = new ArrayList<Role>(dbDaoMock.values());
        Collections.sort(roles, new Comparator<Role>() {
            @Override
            public int compare(Role o1, Role o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return roles;
    }

    @Override
    public Role getRoleById(String roleId) {
        return null;
    }

    @Override
    public void removeRoleById(String roleId) {

    }
}
