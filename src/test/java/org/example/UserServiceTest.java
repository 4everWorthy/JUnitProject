package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testRegisterUser_Success() {
        User user = new User("username1", "password1", "email1@example.com");
        boolean result = userService.registerUser(user);
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_Failure_UserAlreadyExists() {
        User user = new User("username1", "password1", "email1@example.com");
        userService.registerUser(user);

        User sameUser = new User("username1", "password2", "email2@example.com");
        boolean result = userService.registerUser(sameUser);
        assertFalse(result);
    }

    @Test
    public void testRegisterUser_EmptyUsername() {
        User user = new User("", "password1", "email1@example.com");
        boolean result = userService.registerUser(user);
        assertFalse(result);
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User("username1", "password1", "email1@example.com");
        userService.registerUser(user);

        User loggedInUser = userService.loginUser("username1", "password1");
        assertNotNull(loggedInUser);
        assertEquals("username1", loggedInUser.getUsername());
    }

    @Test
    public void testLoginUser_Failure_WrongPassword() {
        User user = new User("username1", "password1", "email1@example.com");
        userService.registerUser(user);

        User loggedInUser = userService.loginUser("username1", "wrongpassword");
        assertNull(loggedInUser);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        User loggedInUser = userService.loginUser("nonexistentUser", "password1");
        assertNull(loggedInUser);
    }

    @Test
    public void testUpdateUserProfile_Success() {
        User user = new User("username1", "password1", "email1@example.com");
        userService.registerUser(user);

        boolean result = userService.updateUserProfile(user, "newUsername", "newPassword", "newEmail@example.com");
        assertTrue(result);
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void testUpdateUserProfile_Failure_NewUsernameTaken() {
        User user1 = new User("username1", "password1", "email1@example.com");
        User user2 = new User("username2", "password2", "email2@example.com");
        userService.registerUser(user1);
        userService.registerUser(user2);

        boolean result = userService.updateUserProfile(user1, "username2", "newPassword", "newEmail@example.com");
        assertFalse(result);
    }

    @Test
    public void testUpdateUserProfile_EmptyFields() {
        User user = new User("username1", "password1", "email1@example.com");
        userService.registerUser(user);

        boolean result = userService.updateUserProfile(user, "", "", "");
        assertFalse(result);
    }
}
