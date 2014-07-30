package com.softserveinc.ita.service.impl;


import com.softserveinc.ita.dao.RoleDAO;
import com.softserveinc.ita.entity.Role;
import com.softserveinc.ita.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }
}
