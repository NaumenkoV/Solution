package com.softserveinc.ita.service;

import com.softserveinc.ita.entity.User;
import com.softserveinc.ita.exception.*;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    User getUserByID (String UserID) throws InvalidUserIDException;
    User getUserByEmail (String email) throws UserEmailNotFoundException;
    List<String> getAllUsersID();
    String deleteUser(String userID);
    User editUser(User user) throws UserDoesNotExistException, EmptyUserException;
    User postNewUser(User user);
    List<User> getUsers();
}
