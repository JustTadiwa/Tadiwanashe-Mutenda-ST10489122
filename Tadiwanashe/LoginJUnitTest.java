
public class LoginJUnitTest {

    
    @Test
    public void testValidUsername() {
        Login login = new Login();
        assertTrue(login.checkUserName("Leo_"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        Login login = new Login();
        assertFalse(login.checkUserName("Leo"));
    }

    @Test
    public void testInvalidUsername_TooLong() {
        Login login = new Login();
        assertFalse(login.checkUserName("Leo_123"));
    }

    @Test
    public void testValidPassword() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("C#isC00l@"));
    }

    @Test
    public void testInvalidPassword_NoUppercase() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("c#isc00l@"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("C#isCool@"));
    }

    @Test
    public void testInvalidPassword_TooShort() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("C#1a@"));
    }

    @Test
    public void testValidPhoneNumber() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27831234567"));
    }

    @Test
    public void testInvalidPhoneNumber_NoCode() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("0831234567"));
    }

    @Test
    public void testInvalidPhoneNumber_TooShort() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("+2712345"));
    }

    @Test
    public void testSuccessfulLogin() {
        Login login = new Login();
        login.registerUser("Leo_", "C#isC00l@", "+27831234567", "Leo", "Smith");
        assertTrue(login.loginUser("Leo_", "C#isC00l@"));
    }

    @Test
    public void testFailedLogin_WrongPassword() {
        Login login = new Login();
        login.registerUser("Leo_", "C#isC00l@", "+27831234567", "Leo", "Smith");
        assertFalse(login.loginUser("Leo_", "WrongPass123"));
    }
}
