import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for Login, using the exact test data from the assignment brief.
 *
 * In NetBeans: right-click this file -> "Test File" (or Run > Test File),
 * making sure JUnit 5 (Jupiter) is added as a library to the project.
 */
public class LoginTest {

    @BeforeEach
    void clearDatabase() {
        // Make sure each test starts with a clean in-memory user list.
        Login.clearRegisteredUsers();
    }

    // ------------------------------------------------------------------
    // Username tests
    // ------------------------------------------------------------------

    @Test
    void testCheckUserName_validUsername_returnsTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    void testCheckUserName_invalidUsername_returnsFalse() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!",
                "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    // ------------------------------------------------------------------
    // Password tests
    // ------------------------------------------------------------------

    @Test
    void testCheckPasswordComplexity_validPassword_returnsTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testCheckPasswordComplexity_invalidPassword_returnsFalse() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    // ------------------------------------------------------------------
    // Cell phone number tests
    // ------------------------------------------------------------------

    @Test
    void testCheckCellPhoneNumber_validNumber_returnsTrue() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    void testCheckCellPhoneNumber_invalidNumber_returnsFalse() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    // ------------------------------------------------------------------
    // Registration message tests
    // ------------------------------------------------------------------

    @Test
    void testRegisterUser_allValid_returnsSuccessMessages() {
        Login login = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        String result = login.registerUser();

        assertTrue(result.contains("Username successfully captured."));
        assertTrue(result.contains("Password successfully captured."));
        assertTrue(result.contains("Cell number successfully captured."));
    }

    @Test
    void testRegisterUser_allInvalid_returnsFailureMessages() {
        Login login = new Login("Kyle", "Smith", "kyle!!!!!!",
                "password", "08966553");
        String result = login.registerUser();

        assertTrue(result.contains("Username is not correctly formatted"));
        assertTrue(result.contains("Password is not correctly formatted"));
        assertTrue(result.contains("Cell number is incorrectly formatted"));
    }

    // ------------------------------------------------------------------
    // Login tests
    // ------------------------------------------------------------------

    @Test
    void testLoginUser_correctCredentials_returnsTrueAndWelcomeMessage() {
        Login registration = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        registration.registerUser();

        Login loginAttempt = new Login(null, null, null, null, null);
        loginAttempt.captureLoginCredentials("kyl_1", "Ch&&sec@ke99!");

        assertTrue(loginAttempt.loginUser());
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                loginAttempt.returnLoginStatus());
    }

    @Test
    void testLoginUser_incorrectCredentials_returnsFalseAndErrorMessage() {
        Login registration = new Login("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        registration.registerUser();

        Login loginAttempt = new Login(null, null, null, null, null);
        loginAttempt.captureLoginCredentials("kyl_1", "WrongPassword1!");

        assertFalse(loginAttempt.loginUser());
        assertEquals("Username or password incorrect, please try again.",
                loginAttempt.returnLoginStatus());
    }
}
