import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login user;

    @BeforeEach
    public void setUp() {
        user = new Login();
    }

    // Username Tests
    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(user.checkUserName("kyl_1"), "Username correctly formatted.");
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(user.checkUserName("kyle!!!!!!!"),
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
    }

    // Password Tests
    @Test
    public void testPasswordMeetsComplexity() {
        assertTrue(user.checkPasswordComplexity("Ch&&sec@ke99!"),
                "Password successfully captured.");
    }

    @Test
    public void testPasswordFailsComplexity() {
        assertFalse(user.checkPasswordComplexity("password"),
                "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }

    // Cell Phone Tests
    @Test
    public void testCellPhoneCorrectFormat() {
        assertTrue(user.checkCellPhone("+27838968976"),
                "Cell number successfully captured.");
    }

    @Test
    public void testCellPhoneIncorrectFormat() {
        assertFalse(user.checkCellPhone("08966553"),
                "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
    }

    //  Registration Tests
    @Test
    public void testRegistrationSuccess() {
        assertTrue(user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976"),
                "User should register successfully with valid data");
    }

    @Test
    public void testRegistrationFailInvalidUsername() {
        assertFalse(user.registerUser("Kyl", "Smith", "kyle", "Ch&&sec@ke99!", "+27838968976"),
                "Registration should fail with invalid username");
    }

    //  Login Tests
    @Test
    public void testLoginSuccessful() {
        // First register a user
        user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        // Then test login
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"),
                "Login should be successful with correct credentials");
    }

    @Test
    public void testLoginFailedWrongUsername() {
        user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.loginUser("wronguser", "Ch&&sec@ke99!"),
                "Login should fail with wrong username");
    }

    @Test
    public void testLoginFailedWrongPassword() {
        user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.loginUser("kyl_1", "wrongpass"),
                "Login should fail with wrong password");
    }

    // Full Name Test
    @Test
    public void testGetFullName() {
        user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Kyl Smith", user.getFullName(),
                "Full name should be correctly formatted");
    }
}