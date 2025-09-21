import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login loginSystem = new Login();

        System.out.println("...User Registration...");
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cell phone number (+27...): ");
        String phoneNumber = input.nextLine();

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // Register user
        String registrationMessage = loginSystem.registerUser(username, password, phoneNumber, firstName, lastName);
        System.out.println("\n" + registrationMessage);

        System.out.println("\n<<-- User Login -->>>");
        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        boolean loginStatus = loginSystem.loginUser(loginUsername, loginPassword);
        String message = loginSystem.returnLoginStatus(loginStatus);
        System.out.println("\n" + message);

        input.close();
    }
}
