import javax.swing.JOptionPane;

public class Login {
    // User details
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userFirstName;
    private String userLastName;

    // USERNAME VALIDATION
    public boolean checkUserName(String userNameInput) {
        return userNameInput.contains("_") && userNameInput.length() <= 5;
    }

    // PASSWORD COMPLEXITY VALIDATION
    public boolean checkPasswordComplexity(String passwordInput) {
        boolean hasUpperCase = !passwordInput.equals(passwordInput.toLowerCase());
        boolean hasDigit = passwordInput.matches(".*\\d.*");
        boolean hasSpecialChar = passwordInput.matches(".*[!@#$%^&*()].*");
        boolean longEnough = passwordInput.length() >= 8;
        return hasUpperCase && hasDigit && hasSpecialChar && longEnough;
    }

    // PHONE NUMBER VALIDATION
    public boolean checkCellPhone(String phoneInput) {
        return phoneInput.matches("^\\+[0-9]{11}$");
    }

    // REGISTER USER
    public boolean registerUser(String firstName, String lastName, String username, String password, String phone) {
        // Username validation
        if (!checkUserName(username)) {
            JOptionPane.showMessageDialog(null, "Username is incorrectly formatted. Must contain '_' and be less than 5 characters.");
            return false;
        }

        // Password validation
        if (!checkPasswordComplexity(password)) {
            JOptionPane.showMessageDialog(null, "Password is incorrectly formatted. Must contain uppercase, digit, special character, and be at least 8 chars long.");
            return false;
        }

        // Phone validation
        if (!checkCellPhone(phone)) {
            JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        }

        // Save user details
        this.userFirstName = firstName;
        this.userLastName = lastName;
        this.userName = username;
        this.userPassword = password;
        this.userPhone = phone;

        JOptionPane.showMessageDialog(null, "User registered successfully!");
        return true;
    }

    // LOGIN USER
    public boolean loginUser(String username, String password) {
        return username.equals(this.userName) && password.equals(this.userPassword);
    }

    // RETURN FULL NAME
    public String getFullName() {
        return userFirstName + " " + userLastName;
    }
}