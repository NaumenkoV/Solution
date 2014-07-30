package com.softserveinc.ita.mocks;

import com.softserveinc.ita.dao.UserDAO;
import com.softserveinc.ita.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Field;
import java.util.*;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertEquals;

public class UserMockDAOTest extends BaseDAOTest {
    @Autowired
    private UserDAO userDAO;
    
    private String validID;
    private String invalidID;

    @Before
    public void setUp() throws Exception {
        userDAO = new UserDAOMockImpl();
        validID = "1";
        invalidID = "ABS";
    }

    @Test
    public void testGetUserByIDReturnsCorrectUser() throws Exception {
        User testUser = new User("1");
        assertEquals(testUser, userDAO.getUserById("1"));
    }

    @Test
    public void testGetUserByIDReturnsNullForInvalidUserID() throws Exception {
        assertNull(userDAO.getUserById(invalidID));
    }

    @Test
    public void testGetUserByIDReturnsNotNullForValidUserID() throws Exception {
        assertNotNull(userDAO.getUserById(validID));
    }

    @Test
    public void testGetAllUsersIDReturnsListOfUsersID() throws Exception {
        ArrayList<String> testUsersIDList = new ArrayList<String>() {{
            add("1");
            add("2");
            add("3");
        }};
        Assert.assertEquals(userDAO.getAllUsersId(), testUsersIDList);
    }

    /**
     * Such strange construction with using reflection
     * are needed because I am testing void method with
     * JUnit rather than Mockito (all attempts to use Mockito
     * failed)
     */
    @Test
    public void testDeleteUser() throws Exception {
        String muserID = "123";

        Set<String> keySet = new HashSet<>();
        Set<String> expected = new HashSet<>();
        expected.add("121");
        expected.add("122");

        userDAO.deleteUserById(muserID);

        userDAO.getClass().getDeclaredField("dbOfUsers");

        Field fld = null;

        try {

            fld = userDAO.getClass().getDeclaredField("dbOfUsers");
            fld.setAccessible(true);
            Map<String, User> mockDB = (Map<String, User>) fld.get(userDAO);
            keySet = mockDB.keySet();

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expected, keySet);
    }

    @Test
    public void testExistingUserEditingAndExpectTrue(){
        User user = new User("id1");
        user.setName("name");
        assertEquals(userDAO.updateUser(user), user);
    }

    @Test
    public void testNotExistingUserAndExpectFalse(){
        User user = new User("id4");
        user.setName("name");
        assertNull(userDAO.updateUser(user));
    }

    @Test
    public void testNullUserParameterAndExpectFalse(){
        User user = null;
        assertNull(userDAO.updateUser(user));
    }

    @Test
    public void testAddNewUserWithOkIDReturnsThatUser() throws Exception {
        User testUser = new User("42");
        Assert.assertEquals(testUser, userDAO.addUser(testUser));
    }

    @Test
    public void testGetUsersAndExpectDefinedList() {
        List<User> sampleUserList = new ArrayList<>();
        Collections.addAll(sampleUserList, new User("id3"), new User("idY"), new User("id09z"));
        Assert.assertEquals(sampleUserList, userDAO.getAllUsers());
    }

}