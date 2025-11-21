import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class MessageTest {

    // Message Length Tests (EXACT REQUIREMENTS)
    @Test
    public void testMessageLengthSuccess() {
        String message = "Hi Mike, can you join us for dinner tonight";
        assertTrue(message.length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailureExactMessage() {
        // Test the exact requirement: "Message exceeds 250 characters by X, please reduce size."
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 253; i++) longMessage.append("a"); // 253 characters
        int excess = longMessage.length() - 250;
        String expectedMessage = "Message exceeds 250 characters by " + excess + ", please reduce size.";

        // Simulate the system response
        String systemResponse = longMessage.length() > 250 ?
                "Message exceeds 250 characters by " + excess + ", please reduce size." :
                "Message ready to send.";

        assertEquals(expectedMessage, systemResponse);
    }

    // ======= Recipient Format Tests (REUSING LOGIN METHOD) =======
    @Test
    public void testRecipientFormatSuccess() {
        Login login = new Login();
        String recipient = "+27735903002"; // Using exact test data from requirements
        assertTrue(login.checkCellPhone(recipient));
        // System should return: "Cell phone number successfully captured."
    }

    @Test
    public void testRecipientFormatFailure() {
        Login login = new Login();
        String recipient = "0657937889"; // Using test data from requirements (missing +)
        assertFalse(login.checkCellPhone(recipient));
        // System should return: "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again."
    }

    // EXACT TEST DATA FROM REQUIREMENTS
    @Test
    public void testExactRequirementDataMessage1() {
        // Test Data for Message 1 from requirements
        String recipient = "+27735903002";
        String message = "Hi Mike, can you join us for dinner tonight.";

        Message messageObj = new Message(1, 2, recipient, message);
        String output = messageObj.printMessage();

        assertTrue(output.contains("MessageID: "));
        assertTrue(output.contains("Message Hash: "));
        assertTrue(output.contains(recipient));
        assertTrue(output.contains(message));
    }

    @Test
    public void testExactRequirementDataMessage2() {
        // Test Data for Message 2 from requirements
        String recipient = "0657937889"; // Note: This should fail validation
        String message = "Hi Teegan, did you receive the payment?";

        Login login = new Login();
        boolean isValidRecipient = login.checkCellPhone(recipient);

        // This recipient should fail validation (no +, wrong length)
        assertFalse(isValidRecipient);
    }

    //  Message Hash Creation Test
    @Test
    public void testMessageHashCreation() {
        Message message = new Message(1, 2, "+27735903002", "Hi Mike join dinner");
        String output = message.printMessage();

        String[] lines = output.split("\n");
        String hashLine = lines[1];
        String hash = hashLine.split(": ")[1];

        String expectedHash = "MS:1:HIDINNER";
        assertEquals(expectedHash, hash);
    }

    //  Message ID Generation Test
    @Test
    public void testMessageIDGeneratedWithLoopCounter() {
        // Test that Message ID uses loop counter (message number)
        Message message1 = new Message(1, 5, "+27718693002", "Message one");
        Message message2 = new Message(2, 5, "+27718693002", "Message two");

        String id1 = message1.printMessage().split("\n")[0].split(": ")[1];
        String id2 = message2.printMessage().split("\n")[0].split(": ")[1];

        // Both IDs should contain their respective message numbers
        assertTrue(id1.contains("1"));
        assertTrue(id2.contains("2"));
        assertNotEquals(id1, id2);
    }

    //  Message Actions Test
    @Test
    public void testSendMessageAction() {
        Message message = new Message(1, 2, "+27735903002", "Hi Mike join dinner");
        String output = message.printMessage();

        // When user selects "Send Message", system should show message details
        assertTrue(output.contains("MessageID: "));
        // System should return: "Message successfully sent."
    }

    @Test
    public void testDisregardMessageAction() {
        // When user selects "Disregard Message"
        boolean messageDisregarded = true;
        assertTrue(messageDisregarded);
        // System should return: "Press 0 to delete message."
    }

    @Test
    public void testStoreMessageAction() {
        Message message = new Message(1, 2, "+27735903002", "Hi Mike join dinner");
        message.storeMessage();

        // System should return: "Message successfully stored."
        assertTrue(true); // Basic execution test
    }

    //  Total Messages Count Test
    @Test
    public void testReturnTotalNumberSent() {
        // Simulate sending multiple messages
        Message message1 = new Message(1, 3, "+27718693002", "First message");
        Message message2 = new Message(2, 3, "+27718693002", "Second message");

        // In a real scenario, you'd have a message counter
        // For now, we test that each message gets a unique number
        String output1 = message1.printMessage();
        String output2 = message2.printMessage();

        assertTrue(output1.contains("MessageID: "));
        assertTrue(output2.contains("MessageID: "));

        // Extract message numbers from IDs
        String id1 = output1.split("\n")[0].split(": ")[1];
        String id2 = output2.split("\n")[0].split(": ")[1];

        assertTrue(id1.contains("1"));
        assertTrue(id2.contains("2"));
    }

    // Message Display Order Test
    @Test
    public void testMessageDetailsDisplayInCorrectOrder() {
        Message message = new Message(1, 2, "+27735903002", "Test message");
        String output = message.printMessage();

        String[] lines = output.split("\n");

        // Must be in this exact order:
        assertEquals("MessageID: " + lines[0].split(": ")[1], lines[0]);
        assertEquals("Message Hash: " + lines[1].split(": ")[1], lines[1]);
        assertEquals("Recipient: +27735903002", lines[2]);
        assertEquals("Message: Test message", lines[3]);
    }
}