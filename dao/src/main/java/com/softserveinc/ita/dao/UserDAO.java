package com.softserveinc.ita.dao;


import com.softserveinc.ita.entity.User;

import java.util.List;

public interface UserDAO {

    User getUserById(String userId);

    User getUserByEmail(String email);

    List<String> getAllUsersId();

    String deleteUserById(String userId);

    User addUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();
}
