import java.util.regex.*;

public class Login {
    // User details
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userFirstName;
    private String userLastName;

    // USERNAME VALIDATION
    // Returns true if username contains "_" and <=5 characters
    public boolean checkUserName(String userNameInput) {
        return userNameInput.contains("_") && userNameInput.length() <= 5;
    }

    // PASSWORD COMPLEXITY VALIDATION
    // Must have uppercase, digit, special char, and >=8 chars
    public boolean checkPasswordComplexity(String passwordInput) {
        boolean hasUpperCase = !passwordInput.equals(passwordInput.toLowerCase());
        boolean hasDigit = passwordInput.matches(".*\\d.*");
        boolean hasSpecialChar = passwordInput.matches(".*[!@#$%^&*()].*");
        boolean longEnough = passwordInput.length() >= 8;
        return hasUpperCase && hasDigit && hasSpecialChar && longEnough;
    }

    // === PHONE NUMBER VALIDATION ===
    // Must start with "+" and have 11 digits after
    public boolean checkCellPhone(String phoneInput) {
        return phoneInput.matches("^\\+[0-9]{11}$");
    }

    // === REGISTER USER ===
    public boolean registerUser(String firstName, String lastName, String username, String password, String phone) {
        // Username validation
        if (!checkUserName(username)) {
            System.out.println("Username is incorrectly formatted. Must contain '_' and be less than 5 characters.");
            return false;
        } else {
            System.out.println("Username successfully captured.");
        }

        // Password validation
        if (!checkPasswordComplexity(password)) {
            System.out.println("Password is incorrectly formatted. Must contain uppercase, digit, special character, and be at least 8 chars long.");
            return false;
        } else {
            System.out.println("Password successfully captured.");
        }

        // Phone validation
        if (!checkCellPhone(phone)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        // Save user details
        this.userFirstName = firstName;
        this.userLastName = lastName;
        this.userName = username;
        this.userPassword = password;
        this.userPhone = phone;

        System.out.println("User registered successfully!");
        return true;
    }

    //  LOGIN USER
    public boolean loginUser(String username, String password) {
        return username.equals(this.userName) && password.equals(this.userPassword);
    }

    //RETURN FULL NAME
    public String getFullName() {
        return userFirstName + " " + userLastName;
    }
}
