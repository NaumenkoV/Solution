package com.softserveinc.ita.service.impl;

import com.softserveinc.ita.dao.UserDAO;
import com.softserveinc.ita.entity.User;
import com.softserveinc.ita.exception.InvalidUserIDException;
import com.softserveinc.ita.exception.EmptyUserException;
import com.softserveinc.ita.exception.UserDoesNotExistException;
import com.softserveinc.ita.exception.UserEmailNotFoundException;
import com.softserveinc.ita.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<String> getAllUsersID() {
        return userDAO.getAllUsersId();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User getUserByID(String UserID) throws InvalidUserIDException {
        if (userDAO.getUserById(UserID) == null) throw new InvalidUserIDException();
        return userDAO.getUserById(UserID);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User getUserByEmail(String email) throws UserEmailNotFoundException {
        if (userDAO.getUserByEmail(email) == null) throw new UserEmailNotFoundException();
        return userDAO.getUserByEmail(email);
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String deleteUser(String userID){
       return userDAO.deleteUserById(userID);
    }
    
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User editUser(User changingUser) throws UserDoesNotExistException, EmptyUserException {
        if (isEmpty(changingUser)) {
            throw new EmptyUserException();
        }
        User editedUser = userDAO.updateUser(changingUser);
        if (editedUser == null) {
            throw new UserDoesNotExistException();
        }
        return editedUser;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public User postNewUser(User user){
        userDAO.addUser(user);
        return user;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    private boolean isEmpty(User changingUser) {
        return (changingUser == null || changingUser.getName().equals(User.DEFAULT_USER_NAME));
    }

}
