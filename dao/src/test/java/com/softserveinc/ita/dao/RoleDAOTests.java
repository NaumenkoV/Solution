package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.Role;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class RoleDAOTests extends BaseDAOTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    RoleDAO roleDAO;

    @Test
    public void testAddRole() {
        Role expected = new Role("ADMIN");
        String roleId = roleDAO.addRole(expected);
        Role actual = (Role) sessionFactory.getCurrentSession().load(Role.class, roleId);
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void testGetAllRoles() {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");
        Role hr = new Role("HR");
        Session session = sessionFactory.getCurrentSession();
        session.save(admin);
        session.save(user);
        session.save(hr);
        List<Role> allRoles = roleDAO.getAllRoles();
        assertThat(3, equalTo(allRoles.size()));
    }

    @Test
    public void testGetRoleById() {
        Role expected = new Role("ADMIN");
        String roleId = (String) sessionFactory.getCurrentSession().save(expected);
        Role actual = roleDAO.getRoleById(roleId);
        assertThat(expected, equalTo(actual));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testRemoveRoleById() {
        Session session = sessionFactory.getCurrentSession();
        Role expected = new Role("ADMIN");
        String roleId = (String) session.save(expected);
        roleDAO.removeRoleById(roleId);
        session.load(Role.class, roleId);
    }
}
