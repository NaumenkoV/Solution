package com.softserveinc.ita.dao.impl;


import com.softserveinc.ita.dao.RoleDAO;
import com.softserveinc.ita.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDAOHibernate implements RoleDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public String addRole(Role role) {
        return (String) sessionFactory.getCurrentSession().save(role);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) sessionFactory.getCurrentSession().createCriteria(Role.class).list();
    }

    @Override
    public Role getRoleById(String roleId) {
        return (Role) sessionFactory.getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("id", roleId)).uniqueResult();
    }

    @Override
    public void removeRoleById(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        Role role = (Role) session.load(Role.class, roleId);
        session.delete(role);
    }
}
