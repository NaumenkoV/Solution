package com.softserveinc.ita.dao.impl;

import com.softserveinc.ita.dao.UserDAO;
import com.softserveinc.ita.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDAOHibernate implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Override
    public User getUserById(String userId) {
        return (User) sessionFactory.getCurrentSession().get(User.class, userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) sessionFactory.getCurrentSession().createCriteria( User.class )
                .add( Restrictions.eq("email", email) )
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getAllUsersId() {
        return (List<String>) sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.projectionList().add(Projections.property("id"))).list();
    }

    @Override
    public String deleteUserById(String userId) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.load(User.class, userId));
        return userId;
    }

    @Override
    public User addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        user.setPassword(encoder.encodePassword(user.getPassword(), null));
        String userId = (String) session.save(user);
        return (User) session.load(User.class, userId);
    }

    @Override
    public User updateUser(User user) {
        String userId = user.getId();
        // password wasn't changed - use password hash from existing user record
        Session session = sessionFactory.getCurrentSession();
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User oldUser = getUserById(userId);
            user.setPassword(oldUser.getPassword());
            session.evict(oldUser);
        } else {
            user.setPassword(encoder.encodePassword(user.getPassword(), null));
        }

        session.update(user);
        return (User) session.load(User.class, userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllUsers() {
        return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }
}
