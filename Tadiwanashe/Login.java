public class Login {
    // User details
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userFirstName;
    private String userLastName;

    // Check username
    public boolean checkUserName(String userNameInput) {
        return userNameInput.contains("_") && userNameInput.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String passwordInput) {
        boolean hasUppercase = !passwordInput.equals(passwordInput.toLowerCase());
        boolean hasDigit = passwordInput.matches(".*[0-9].*");
        boolean hasSpecialChar = passwordInput.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        boolean longEnough = passwordInput.length() >= 8;

        return hasUppercase && hasDigit && hasSpecialChar && longEnough;
    }

    // Check cell phone number format
    public boolean checkCellPhoneNumber(String phoneInput) {
        String regex = "^\\+27\\d{9,10}$"; // Must start with +27 and have 9–10 digits
        return phoneInput.matches(regex);
    }

    // Register user with detailed feedback
    public String registerUser(String userNameInput, String passwordInput, String phoneInput,
                               String firstNameInput, String lastNameInput) {
        StringBuilder message = new StringBuilder();

        boolean validUsername = checkUserName(userNameInput);
        boolean validPassword = checkPasswordComplexity(passwordInput);
        boolean validPhone = checkCellPhoneNumber(phoneInput);

        if (validUsername) {
            message.append("Username successfully captured.\n");
        } else {
            message.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
        }

        if (validPassword) {
            message.append("Password successfully captured.\n");
        } else {
            message.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        }

        if (validPhone) {
            message.append("Cell phone number successfully added.\n");
        } else {
            message.append("Cell phone number incorrectly formatted or does not contain international code.\n");
        }

        if (validUsername && validPassword && validPhone) {
            // Save details
            userName = userNameInput;
            userPassword = passwordInput;
            userPhone = phoneInput;
            userFirstName = firstNameInput;
            userLastName = lastNameInput;

            message.append("User registered successfully!");
        }

        return message.toString();
    }

    // Login check
    public boolean loginUser(String userNameInput, String passwordInput) {
        return userName != null && userPassword != null &&
               userName.equals(userNameInput) && userPassword.equals(passwordInput);
    }

    // Login status message
    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Welcome " + userFirstName + " " + userLastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Optional: Display user info
    @Override
    public String toString() {
        return "Name: " + userFirstName + " " + userLastName +
               "\nUsername: " + userName +
               "\nPhone: " + userPhone;
    }
}
