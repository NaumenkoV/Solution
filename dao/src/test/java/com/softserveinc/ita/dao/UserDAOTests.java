package com.softserveinc.ita.dao;

import com.softserveinc.ita.entity.User;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class UserDAOTests extends BaseDAOTest {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testGetUserById() {
        User expected = new User("TestUserOne", "Test");
        String userID = (String) sessionFactory.getCurrentSession().save(expected);
        User actual = userDAO.getUserById(userID);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByEmail() {
        String testEmail = "test@test.com";
        User expected = new User("TestUserOne", "Test");
        expected.setEmail(testEmail);
        String userID = (String) sessionFactory.getCurrentSession().save(expected);
        User actual = userDAO.getUserByEmail(testEmail);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddUser() {
        User expected = new User("TestUser", "Test");
        User user = userDAO.addUser(expected);
        User actual = (User) sessionFactory.getCurrentSession().load(User.class, user.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        User expectedOne = new User("TestUserOne", "Test");
        User expectedTwo = new User("TestUserTwo", "Test");
        User expectedThree = new User("TestUserThree", "Test");
        session.save(expectedOne);
        session.save(expectedTwo);
        session.save(expectedThree);
        List<User> allUsers = userDAO.getAllUsers();
        int actualSize = allUsers.size();
        assertEquals(3, actualSize);
    }

    @Test
    public void testUpdateUser() {
        Session session = sessionFactory.getCurrentSession();
        User expected = new User("Expected", "Test");
        String userID = (String) session.save(expected);
        expected.setName("Actual");
        userDAO.updateUser(expected);
        User actual = (User) session.load(User.class, userID);
        System.out.println(actual.toString());
        assertThat(expected, equalTo(actual));
    }

    @Test
    public void testGetAllUsersId() {
        Session session = sessionFactory.getCurrentSession();
        User expectedOne = new User("TestUser1", "Test");
        User expectedTwo = new User("TestUser2", "Test");
        User expectedThree = new User("TestUser3", "Test");
        session.save(expectedOne);
        session.save(expectedTwo);
        session.save(expectedThree);
        List<String> allUsersId = userDAO.getAllUsersId();
        int actual = allUsersId.size();
        assertEquals(3, actual);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = ObjectNotFoundException.class)
    public void testDeleteUserById() {
        Session session = sessionFactory.getCurrentSession();
        String userId = (String) session.save(new User());
        userDAO.deleteUserById(userId);
        session.load(User.class, userId);
    }
}