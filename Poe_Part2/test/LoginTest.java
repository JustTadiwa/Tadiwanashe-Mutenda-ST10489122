import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private Login user;

    @BeforeEach
    public void setUp() {
        user = new Login();
        // Register a valid user for login tests
        user.registerUser("Kyl", "Smith", "kyl_1", "Ch&&sec@ke991", "+27838968976");
    }

    // ======= Username Tests =======
    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(user.checkUserName("kyl_1"), "Username correctly formatted.");
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(user.checkUserName("kyle!!!!!!!"),
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
    }

    // ======= Password Tests =======
    @Test
    public void testPasswordMeetsComplexity() {
        assertTrue(user.checkPasswordComplexity("Ch&&sec@ke991"), "Password successfully captured.");
    }

    @Test
    public void testPasswordFailsComplexity() {
        assertFalse(user.checkPasswordComplexity("password"),
                "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
    }

    // ======= Cell Phone Tests =======
    @Test
    public void testCellPhoneCorrectFormat() {
        assertTrue(user.checkCellPhone("+27838968976"), "Cell number successfully captured.");
    }

    @Test
    public void testCellPhoneIncorrectFormat() {
        assertFalse(user.checkCellPhone("08966553"),
                "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.");
    }

    // ======= Login Tests =======
    @Test
    public void testLoginSuccessful() {
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke991"), "Login Successful");
    }

    @Test
    public void testLoginFailedWrongUsername() {
        assertFalse(user.loginUser("wronguser", "Ch&&sec@ke991"), "Login Failed");
    }

    @Test
    public void testLoginFailedWrongPassword() {
        assertFalse(user.loginUser("kyl_1", "wrongpass"), "Login Failed");
    }

    // ======= Full Name Test =======
    @Test
    public void testGetFullName() {
        assertEquals("Kyl Smith", user.getFullName());
    }
}
