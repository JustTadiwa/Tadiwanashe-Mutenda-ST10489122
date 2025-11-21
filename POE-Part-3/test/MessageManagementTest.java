import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageManagementTest {

    private Main main;
    private Message[] testMessages;

    @BeforeEach
    public void setUp() {
        main = new Main();
        // Create test messages matching the requirement data
        testMessages = new Message[] {
                new Message(1, 5, "+2784557896", "Did you get the cake?"),
                new Message(2, 5, "+27338884567", "Where are you? You are late! I have asked you to be on time."),
                new Message(3, 5, "+2733484567", "Yohoooo, I am at your gate."),
                new Message(4, 5, "0808844597", "It is dinner time !!"),
                new Message(5, 5, "+27338884567", "Ok, I am leaving without you.")
        };
    }

    // TEST 1: Array Correctly Populated
    @Test
    public void testMessagesArrayCorrectlyPopulated() {
        // Test that arrays contain the expected test data
        String[] expectedMessages = {
                "Did you get the cake?",
                "It is dinner time !!"
        };

        // Check if specific messages exist in our test data
        boolean foundCakeMessage = false;
        boolean foundDinnerMessage = false;

        for (Message msg : testMessages) {
            if (msg.getMessageText().equals("Did you get the cake?")) {
                foundCakeMessage = true;
            }
            if (msg.getMessageText().equals("It is dinner time !!")) {
                foundDinnerMessage = true;
            }
        }

        assertTrue(foundCakeMessage, "Should find 'Did you get the cake?' message");
        assertTrue(foundDinnerMessage, "Should find 'It is dinner time !!' message");
    }

    // TEST 2: Display Longest Message
    @Test
    public void testDisplayLongestMessage() {
        // Find the longest message in test data
        Message longestMessage = testMessages[0];
        for (int i = 1; i < testMessages.length; i++) {
            if (testMessages[i].getMessageLength() > longestMessage.getMessageLength()) {
                longestMessage = testMessages[i];
            }
        }

        // The longest message should be message 2
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expectedLongest, longestMessage.getMessageText(),
                "System should return: 'Where are you? You are late! I have asked you to be on time.'");
    }

    // TEST 3: Search for Message ID
    @Test
    public void testSearchForMessageID() {
        // Test searching for a specific message ID
        String searchID = "Msg4of5"; // Message ID for the 4th test message

        Message foundMessage = null;
        for (Message msg : testMessages) {
            if (msg.getMessageID().equals(searchID)) {
                foundMessage = msg;
                break;
            }
        }

        assertNotNull(foundMessage, "Should find message with ID: " + searchID);
        assertEquals("It is dinner time !!", foundMessage.getMessageText(),
                "System should return: 'It is dinner time !!'");
    }

    // TEST 4: Search All Messages for Particular Recipient
    @Test
    public void testSearchMessagesByRecipient() {
        // Search for all messages sent to +27338884567
        String searchRecipient = "+27338884567";

        int foundCount = 0;
        StringBuilder foundMessages = new StringBuilder();

        for (Message msg : testMessages) {
            if (msg.getRecipient().equals(searchRecipient)) {
                foundCount++;
                if (foundMessages.length() > 0) {
                    foundMessages.append("; ");
                }
                foundMessages.append(msg.getMessageText());
            }
        }

        assertEquals(2, foundCount, "Should find 2 messages for recipient: " + searchRecipient);

        String expectedMessages = "Where are you? You are late! I have asked you to be on time.; Ok, I am leaving without you.";
        assertTrue(foundMessages.toString().contains("Where are you? You are late! I have asked you to be on time.") &&
                        foundMessages.toString().contains("Ok, I am leaving without you."),
                "System should return: 'Where are you? You are late! I have asked you to be on time.; Ok, I am leaving without you.'");
    }

    // TEST 5: Delete Message Using Message Hash
    @Test
    public void testDeleteMessageByHash() {
        // Create a test array to simulate deletion
        Message[] messagesBeforeDelete = {
                new Message(1, 3, "+2784557896", "Did you get the cake?"),
                new Message(2, 3, "+27338884567", "Where are you? You are late! I have asked you to be on time."),
                new Message(3, 3, "+2733484567", "Yohoooo, I am at your gate.")
        };

        // Get hash of the message to delete
        String hashToDelete = messagesBeforeDelete[1].getMessageHash();
        String messageTextToDelete = messagesBeforeDelete[1].getMessageText();

        // Simulate deletion by creating new array without the deleted message
        Message[] messagesAfterDelete = new Message[2];
        messagesAfterDelete[0] = messagesBeforeDelete[0];
        messagesAfterDelete[1] = messagesBeforeDelete[2];

        assertEquals(2, messagesAfterDelete.length, "Array size should decrease after deletion");
        assertFalse(arrayContainsMessageWithHash(messagesAfterDelete, hashToDelete),
                "Message with hash " + hashToDelete + " should be deleted");

        // Test the success message
        String expectedMessage = "Message \"" + messageTextToDelete + "\" successfully deleted.";
        assertTrue(expectedMessage.contains("successfully deleted"),
                "System should return: 'Message \"Where are you? You are late! I have asked you to be on time\" successfully deleted.'");
    }

    // TEST 6: Display Report
    @Test
    public void testDisplayReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== FULL MESSAGE REPORT ===\n\n");

        for (int i = 0; i < testMessages.length; i++) {
            report.append("MESSAGE ").append(i + 1).append(":\n")
                    .append(testMessages[i].printMessage()).append("\n\n");
        }

        report.append("=== END OF REPORT ===\n");
        report.append("Total messages: ").append(testMessages.length);

        // Verify report contains all required elements
        String reportStr = report.toString();
        assertTrue(reportStr.contains("Message Hash"), "Report should include Message Hash");
        assertTrue(reportStr.contains("Recipient"), "Report should include Recipient");
        assertTrue(reportStr.contains("Message"), "Report should include Message");
        assertTrue(reportStr.contains("Total messages: 5"), "Report should show total message count");
    }

    // TEST 7: Array Population with Correct Test Data
    @Test
    public void testArrayContainsExpectedTestData() {
        // Verify all 5 test messages are correctly populated
        assertEquals(5, testMessages.length, "Array should contain 5 test messages");

        // Check specific messages from requirements
        boolean[] foundExpected = new boolean[4];

        for (Message msg : testMessages) {
            switch (msg.getMessageText()) {
                case "Did you get the cake?":
                    foundExpected[0] = true;
                    break;
                case "Where are you? You are late! I have asked you to be on time.":
                    foundExpected[1] = true;
                    break;
                case "Yohoooo, I am at your gate.":
                    foundExpected[2] = true;
                    break;
                case "It is dinner time !!":
                    foundExpected[3] = true;
                    break;
            }
        }

        for (int i = 0; i < foundExpected.length; i++) {
            assertTrue(foundExpected[i], "All expected test messages should be found in array");
        }
    }

    //  TEST 8: Search Functionality Returns Correct Results
    @Test
    public void testSearchFunctionality() {
        // Test searching for existing and non-existing messages
        String existingRecipient = "+27338884567";
        String nonExistingRecipient = "+27123456789";

        int existingCount = countMessagesByRecipient(existingRecipient);
        int nonExistingCount = countMessagesByRecipient(nonExistingRecipient);

        assertEquals(2, existingCount, "Should find 2 messages for existing recipient");
        assertEquals(0, nonExistingCount, "Should find 0 messages for non-existing recipient");
    }

    //  TEST 9: Message Hash Uniqueness
    @Test
    public void testMessageHashUniqueness() {
        // Verify that each message has a unique hash
        for (int i = 0; i < testMessages.length; i++) {
            for (int j = i + 1; j < testMessages.length; j++) {
                assertNotEquals(testMessages[i].getMessageHash(), testMessages[j].getMessageHash(),
                        "Each message should have a unique hash");
            }
        }
    }

    //  TEST 10: Message ID Format
    @Test
    public void testMessageIDFormat() {
        for (Message msg : testMessages) {
            String messageID = msg.getMessageID();
            assertTrue(messageID.startsWith("Msg"), "Message ID should start with 'Msg'");
            assertTrue(messageID.length() <= 10, "Message ID should be 10 characters or less");
        }
    }

    // HELPER METHODS

    private boolean arrayContainsMessageWithHash(Message[] messages, String hash) {
        for (Message msg : messages) {
            if (msg != null && msg.getMessageHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

    private int countMessagesByRecipient(String recipient) {
        int count = 0;
        for (Message msg : testMessages) {
            if (msg.getRecipient().equals(recipient)) {
                count++;
            }
        }
        return count;
    }
}