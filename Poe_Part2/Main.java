import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create a Scanner object to get user input
        Scanner input = new Scanner(System.in);
        // Create a Login object to handle registration and login
        Login user = new Login();

        // === USER REGISTRATION SECTION ===
        System.out.println("=== QuickChat Registration ===");
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        System.out.print("Enter phone number (e.g. +27123456789): ");
        String phone = input.nextLine();

        // Call the registerUser method to validate and register the user
        boolean isRegistered = user.registerUser(firstName, lastName, username, password, phone);

        // If registration fails, exit the program
        if (!isRegistered) {
            System.out.println("Registration failed. Exiting program.");
            return;
        }

        // === LOGIN SECTION ===
        System.out.println("\nWelcome to QuickChat!");
        System.out.print("Enter username to login: ");
        String loginUser = input.nextLine();
        System.out.print("Enter password to login: ");
        String loginPass = input.nextLine();

        // Validate user login credentials
        if (!user.loginUser(loginUser, loginPass)) {
            System.out.println("Invalid login. Exiting program.");
            return;
        }

        // === MAIN MENU SECTION ===
        int option;
        do {
            // Display menu options to the user
            System.out.println("\nMenu:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show Recently Sent Messages");
            System.out.println("3) Quit");
            System.out.print("Choose option (1-3): ");
            option = input.nextInt();
            input.nextLine(); // consume newline

            // if else ladder(more efficient)
            if (option == 1) {
                // Ask user how many messages they want to send
                System.out.print("How many messages do you want to send? ");
                int numMessages = input.nextInt();
                input.nextLine();

                // Loop through to allow user to send multiple messages
                for (int i = 0; i < numMessages; i++) {
                    System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");
                    System.out.print("Enter recipient (e.g. +27123456789): ");
                    String recipient = input.nextLine();

                    // Validate recipient phone number format
                    if (!Message.checkRecipientCell(recipient)) {
                        System.out.println("Invalid recipient number format!");
                        i--;
                        continue;
                    }

                    // Capture message text
                    System.out.print("Enter message (max 250 chars): ");
                    String messageText = input.nextLine();

                    // Ensure message is not too long
                    if (messageText.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        i--;
                        continue;
                    }

                    // Provide message action options
                    System.out.println("Choose action for this message:");
                    System.out.println("1) Send");
                    System.out.println("2) Disregard");
                    System.out.println("3) Store");
                    System.out.print("Select (1-3): ");
                    int action = input.nextInt();
                    input.nextLine();

                    // Handle user choice
                    if (action == 1) {
                        System.out.println("Message sent!");
                        Message.sendMessage(recipient, messageText);
                    } else if (action == 2) {
                        System.out.println("Message disregarded.");
                    } else if (action == 3) {
                        System.out.println("Message stored for later.");
                        Message.storeMessage(recipient, messageText);
                    } else {
                        System.out.println("Invalid option.");
                    }
                }

                // Display a summary of messages using JOptionPane
                JOptionPane.showMessageDialog(null, Message.printMessages(),
                        "Messages Summary", JOptionPane.INFORMATION_MESSAGE);

            } else if (option == 2) {
                // Option for viewing recent messages (to be implemented later)
                System.out.println("Coming Soon.");
            } else if (option == 3) {
                // Exit the application
                System.out.println("Exiting QuickChat. Goodbye!");
            } else {
                // Handle invalid inputs
                System.out.println("Invalid option. Try again.");
            }

        } while (option != 3); // Loop until the user chooses to quit
    }
}
