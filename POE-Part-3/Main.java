import javax.swing.JOptionPane;

public class Main {
    // PART 3: Array to store ALL sent messages persistently
    private static Message[] allMessages = new Message[100]; // Array for up to 100 messages
    private static int totalMessagesCount = 0; // Track how many messages are stored

    public static void main(String[] args) {
        Login user = new Login();

        // USER REGISTRATION SECTION
        JOptionPane.showMessageDialog(null, "=== QuickChat Registration ===");
        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");
        String phone = JOptionPane.showInputDialog("Enter phone number (e.g. +27123456789):");

        // Check if user cancelled registration
        if (firstName == null || lastName == null || username == null || password == null || phone == null) {
            JOptionPane.showMessageDialog(null, "Registration cancelled. Exiting program.");
            return;
        }

        boolean isRegistered = user.registerUser(firstName, lastName, username, password, phone);
        if (!isRegistered) {
            JOptionPane.showMessageDialog(null, "Registration failed. Exiting program.");
            return;
        }

        //  LOGIN SECTION
        // Welcome message as per requirement
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        String loginUser = JOptionPane.showInputDialog("Enter username to login:");
        String loginPass = JOptionPane.showInputDialog("Enter password to login:");

        // Check if user cancelled login
        if (loginUser == null || loginPass == null) {
            JOptionPane.showMessageDialog(null, "Login cancelled. Exiting program.");
            return;
        }

        if (!user.loginUser(loginUser, loginPass)) {
            JOptionPane.showMessageDialog(null, "Invalid login. Exiting program.");
            return;
        }

        // PART 3: LOAD TEST DATA INTO ARRAY
        loadTestData();

        // MAIN MENU SECTION
        int option;
        do {
            // PART 3: Extended menu with new options
            String menuInput = JOptionPane.showInputDialog(
                    "Menu:\n" +
                            "1) Send Messages\n" +
                            "2) Show Recently Sent Messages\n" +
                            "3) Message Management\n" +  // NEW: Part 3 features
                            "4) Quit\n\n" +
                            "Choose option (1-4):"
            );

            // Check for cancel button
            if (menuInput == null) {
                option = 4; // Treat cancel as Quit
            } else {
                try {
                    option = Integer.parseInt(menuInput);
                } catch (NumberFormatException e) {
                    option = 0; // Invalid input
                }
            }

            if (option == 1) {
                sendMessages(user);
            } else if (option == 2) {
                showRecentMessages();
            } else if (option == 3) {
                messageManagement(); // NEW: Part 3 features
            } else if (option == 4) {
                JOptionPane.showMessageDialog(null, "Exiting QuickChat. Goodbye, " + user.getFullName() + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1, 2, 3, or 4.");
            }

        } while (option != 4);
    }

    // PART 3: LOAD TEST DATA
    private static void loadTestData() {
        // Add the required test data to our array
        allMessages[totalMessagesCount++] = new Message(1, 5, "+2784557896", "Did you get the cake?");
        allMessages[totalMessagesCount++] = new Message(2, 5, "+27338884567", "Where are you? You are late! I have asked you to be on time.");
        allMessages[totalMessagesCount++] = new Message(3, 5, "+2733484567", "Yohoooo, I am at your gate.");
        allMessages[totalMessagesCount++] = new Message(4, 5, "0808844597", "It is dinner time !!");
        allMessages[totalMessagesCount++] = new Message(5, 5, "+27338884567", "Ok, I am leaving without you.");

        JOptionPane.showMessageDialog(null, "Test data loaded successfully! " + totalMessagesCount + " messages in system.");
    }

    // === EXISTING SEND MESSAGES METHOD (UPDATED) ===
    private static void sendMessages(Login user) {
        // Get number of messages using JOptionPane
        String numMsgInput = JOptionPane.showInputDialog("How many messages do you want to send?");
        if (numMsgInput == null) {
            return; // User cancelled
        }

        int numMessages;
        try {
            numMessages = Integer.parseInt(numMsgInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return;
        }

        // Use array to store messages (as taught in Learning Unit 6)
        Message[] sessionMessages = new Message[numMessages];
        int messagesCount = 0;

        // For loop as explicitly required by rubric
        for (int i = 0; i < numMessages; i++) {
            int currentMessageNum = totalMessagesCount + 1; // Use global counter

            String recipient = JOptionPane.showInputDialog(
                    "--- Message " + (i + 1) + " of " + numMessages + " ---\n" +
                            "Enter recipient (e.g. +27123456789):"
            );

            if (recipient == null) {
                break; // User cancelled message entry
            }

            // Validate recipient
            if (!user.checkCellPhone(recipient)) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number format! Must start with '+' and have 11 digits.");
                i--; // Repeat this message
                continue;
            }

            String messageText = JOptionPane.showInputDialog("Enter message (max 250 chars):");
            if (messageText == null) {
                break; // User cancelled message entry
            }

            if (messageText.length() > 250) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                i--;
                continue;
            }

            // Create a new Message object using the loop counter
            Message newMessage = new Message(currentMessageNum, numMessages, recipient, messageText);

            // Action menu
            String actionInput = JOptionPane.showInputDialog(
                    "Choose action for this message:\n" +
                            "1) Send\n" +
                            "2) Disregard\n" +
                            "3) Store\n\n" +
                            "Select (1-3):"
            );

            if (actionInput == null) {
                break; // User cancelled
            }

            int action;
            try {
                action = Integer.parseInt(actionInput);
            } catch (NumberFormatException e) {
                action = 0; // Invalid input
            }

            if (action == 1 || action == 3) {
                // Store message in session array and global array
                sessionMessages[messagesCount] = newMessage;
                messagesCount++;

                // PART 3: Also add to persistent array
                if (totalMessagesCount < allMessages.length) {
                    allMessages[totalMessagesCount] = newMessage;
                    totalMessagesCount++;
                }

                if (action == 1) {
                    JOptionPane.showMessageDialog(null, "Message sent!\n\n" + newMessage.printMessage());
                } else { // action == 3
                    newMessage.storeMessage();
                }
            } else if (action == 2) {
                JOptionPane.showMessageDialog(null, "Message disregarded.");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid option. Message disregarded.");
            }
        }

        // Display summary of all messages
        if (messagesCount > 0) {
            StringBuilder summary = new StringBuilder();
            summary.append("--- All Messages Summary ---\n");
            for (int j = 0; j < messagesCount; j++) {
                if (sessionMessages[j] != null) {
                    summary.append(sessionMessages[j].printMessage()).append("\n-----\n");
                }
            }
            summary.append("Total messages: ").append(messagesCount);
            JOptionPane.showMessageDialog(null, summary.toString());
        }
    }

    // === EXISTING SHOW RECENT MESSAGES ===
    private static void showRecentMessages() {
        JOptionPane.showMessageDialog(null, "Coming Soon.");
    }

    // PART 3: MESSAGE MANAGEMENT MENU
    private static void messageManagement() {
        int managementOption;
        do {
            String managementInput = JOptionPane.showInputDialog(
                    "Message Management:\n" +
                            "1) Display All Messages (Sender/Recipient)\n" +
                            "2) Find Longest Message\n" +
                            "3) Search by Message ID\n" +
                            "4) Search by Recipient\n" +
                            "5) Delete by Message Hash\n" +
                            "6) Display Full Report\n" +
                            "7) Back to Main Menu\n\n" +
                            "Choose option (1-7):"
            );

            if (managementInput == null) {
                managementOption = 7; // Treat cancel as Back
            } else {
                try {
                    managementOption = Integer.parseInt(managementInput);
                } catch (NumberFormatException e) {
                    managementOption = 0;
                }
            }

            switch (managementOption) {
                case 1:
                    displayAllMessages();
                    break;
                case 2:
                    findLongestMessage();
                    break;
                case 3:
                    searchByMessageID();
                    break;
                case 4:
                    searchByRecipient();
                    break;
                case 5:
                    deleteByMessageHash();
                    break;
                case 6:
                    displayFullReport();
                    break;
                case 7:
                    JOptionPane.showMessageDialog(null, "Returning to main menu.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose 1-7.");
            }
        } while (managementOption != 7);
    }

    // === PART 3: IMPLEMENTATION OF NEW FEATURES ===

    // 1. Display All Messages (Sender/Recipient)
    private static void displayAllMessages() {
        if (totalMessagesCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages found in the system.");
            return;
        }

        StringBuilder output = new StringBuilder();
        output.append("=== ALL MESSAGES ===\n");
        for (int i = 0; i < totalMessagesCount; i++) {
            if (allMessages[i] != null) {
                output.append("Message ").append(i + 1).append(": ")
                        .append(allMessages[i].getBasicInfo()).append("\n");
            }
        }
        output.append("\nTotal: ").append(totalMessagesCount).append(" messages");
        JOptionPane.showMessageDialog(null, output.toString());
    }

    // 2. Find Longest Message
    private static void findLongestMessage() {
        if (totalMessagesCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages found in the system.");
            return;
        }

        Message longestMessage = allMessages[0];
        for (int i = 1; i < totalMessagesCount; i++) {
            if (allMessages[i] != null &&
                    allMessages[i].getMessageLength() > longestMessage.getMessageLength()) {
                longestMessage = allMessages[i];
            }
        }

        JOptionPane.showMessageDialog(null,
                "=== LONGEST MESSAGE ===\n" +
                        "Length: " + longestMessage.getMessageLength() + " characters\n" +
                        longestMessage.printMessage());
    }

    // 3. Search by Message ID
    private static void searchByMessageID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search for:");
        if (searchID == null || searchID.trim().isEmpty()) {
            return;
        }

        StringBuilder output = new StringBuilder();
        output.append("=== SEARCH RESULTS FOR ID: ").append(searchID).append(" ===\n");
        boolean found = false;

        for (int i = 0; i < totalMessagesCount; i++) {
            if (allMessages[i] != null && allMessages[i].getMessageID().equalsIgnoreCase(searchID)) {
                output.append(allMessages[i].printMessage()).append("\n");
                found = true;
                break; // Assuming unique IDs
            }
        }

        if (!found) {
            output.append("No message found with ID: ").append(searchID);
        }

        JOptionPane.showMessageDialog(null, output.toString());
    }

    // 4. Search by Recipient
    private static void searchByRecipient() {
        String searchRecipient = JOptionPane.showInputDialog("Enter recipient phone number to search for:");
        if (searchRecipient == null || searchRecipient.trim().isEmpty()) {
            return;
        }

        StringBuilder output = new StringBuilder();
        output.append("=== MESSAGES FOR RECIPIENT: ").append(searchRecipient).append(" ===\n");
        boolean found = false;

        for (int i = 0; i < totalMessagesCount; i++) {
            if (allMessages[i] != null && allMessages[i].getRecipient().equals(searchRecipient)) {
                output.append("Message ").append(i + 1).append(":\n")
                        .append(allMessages[i].printMessage()).append("\n-----\n");
                found = true;
            }
        }

        if (!found) {
            output.append("No messages found for recipient: ").append(searchRecipient);
        }

        JOptionPane.showMessageDialog(null, output.toString());
    }

    // 5. Delete by Message Hash
    private static void deleteByMessageHash() {
        String searchHash = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (searchHash == null || searchHash.trim().isEmpty()) {
            return;
        }

        boolean found = false;
        for (int i = 0; i < totalMessagesCount; i++) {
            if (allMessages[i] != null && allMessages[i].getMessageHash().equals(searchHash)) {
                String deletedMessage = allMessages[i].getMessageText();
                // Remove the message by shifting array elements
                for (int j = i; j < totalMessagesCount - 1; j++) {
                    allMessages[j] = allMessages[j + 1];
                }
                allMessages[totalMessagesCount - 1] = null;
                totalMessagesCount--;

                JOptionPane.showMessageDialog(null,
                        "Message \"" + deletedMessage + "\" successfully deleted.");
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No message found with hash: " + searchHash);
        }
    }

    // 6. Display Full Report
    private static void displayFullReport() {
        if (totalMessagesCount == 0) {
            JOptionPane.showMessageDialog(null, "No messages found in the system.");
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("=== FULL MESSAGE REPORT ===\n\n");

        for (int i = 0; i < totalMessagesCount; i++) {
            if (allMessages[i] != null) {
                report.append("MESSAGE ").append(i + 1).append(":\n")
                        .append(allMessages[i].printMessage()).append("\n\n");
            }
        }

        report.append("=== END OF REPORT ===\n");
        report.append("Total messages: ").append(totalMessagesCount);

        JOptionPane.showMessageDialog(null, report.toString());
    }
}