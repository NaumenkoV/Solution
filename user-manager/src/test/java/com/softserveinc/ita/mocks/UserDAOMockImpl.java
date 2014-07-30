package com.softserveinc.ita.mocks;

import com.softserveinc.ita.dao.UserDAO;
import com.softserveinc.ita.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDAOMockImpl implements UserDAO {
    private Map<String, User> dbOfUsers;

    public UserDAOMockImpl() {
        dbOfUsers = new HashMap<>();
        dbOfUsers.put("121", new User("121"));
        dbOfUsers.put("122", new User("122"));
        dbOfUsers.put("123", new User("123"));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Collections.addAll(users, new User("id3"), new User("idY"), new User("id09z"));
        return users;
    }

    @Override
    public User getUserById(String UserID) {
        User user = null;
        ArrayList<User> users = new ArrayList<User>() {{
            add(new User("1"));
            add(new User("2"));
        }};
        for (User u : users) {
            if (u.getId().equals(UserID)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        if(email.equals("no@such.com")){
            return null;
        }
        User user = new User("1");
        user.setEmail(email);
        return user;
    }

    @Override
    public ArrayList<String> getAllUsersId() {
        ArrayList<String> usersID = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        return usersID;
    }
    public User addUser(User user){
        ArrayList<User> users = new ArrayList<User>() {{
            add(new User("1"));
            add(new User("2"));
        }};
        return user;
    }
    
    @Override
    public String deleteUserById(String userID){
        dbOfUsers.remove(userID);
        return userID;
    }

    @Override
    public User updateUser(User changingUser){
        if(changingUser==null){
            return null;
        }
        List<User> users = new ArrayList<User>(){
            {
                add(new User("id1"));
                add(new User("id2"));
                add(new User("id3"));
            }
        };
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(changingUser.getId())) {
                users.set(i, changingUser);
                return users.get(i);
            }
        }
        return null;
    }
}
