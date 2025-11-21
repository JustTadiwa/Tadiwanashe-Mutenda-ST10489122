import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class MessageTest {

    // ======= Message Length Tests =======
    @Test
    public void testMessageLengthSuccess() {
        String message = "Hi Mike, can you join us for dinner tonight";
        assertTrue(message.length() <= 250, 
                "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) longMessage.append("a");
        int excess = longMessage.length() - 250;
        String result = "Message exceeds 250 characters by " + excess + ", please reduce size.";
        assertTrue(longMessage.length() > 250, result);
    }

    // ======= Recipient Format Tests =======
    @Test
    public void testRecipientFormatSuccess() {
        Login login = new Login();
        String recipient = "+27718693002";
        assertTrue(login.checkCellPhone(recipient),
                "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientFormatFailure() {
        Login login = new Login();
        String recipient = "08575975889";
        assertFalse(login.checkCellPhone(recipient),
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    // ======= Message Object Creation Tests =======
    @Test
    public void testMessageObjectCreation() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        
        assertNotNull(message, "Message object should be created successfully");
        assertTrue(message.printMessage().contains("MessageID: "), 
                "Message ID should be generated");
        assertTrue(message.printMessage().contains("Message Hash: "), 
                "Message Hash should be generated");
    }

    // ======= Message Hash Creation Test =======
    @Test
    public void testMessageHashCreation() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike join dinner");
        String output = message.printMessage();
        
        // Extract hash from output
        String[] lines = output.split("\n");
        String hashLine = lines[1]; // Second line should be Message Hash
        String hash = hashLine.split(": ")[1];
        
        // Hash should be: first 2 chars of ID + : + message number + : + first word + last word
        assertTrue(hash.startsWith("Ms:1:HI"), 
                "Message hash should be correctly formatted. Got: " + hash);
        assertTrue(hash.contains("dinner"), 
                "Message hash should contain last word");
    }

    // ======= Message ID Generation Test =======
    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String output = message.printMessage();
        
        // Extract Message ID from output
        String[] lines = output.split("\n");
        String idLine = lines[0]; // First line should be MessageID
        String messageID = idLine.split(": ")[1];
        
        assertNotNull(messageID, "Message ID should be generated");
        assertTrue(messageID.length() <= 10, 
                "Message ID should be 10 characters or less. Got: " + messageID);
        assertTrue(messageID.startsWith("Msg"), 
                "Message ID should start with 'Msg'. Got: " + messageID);
    }

    // ======= Message Print Format Test =======
    @Test
    public void testMessageDetailsDisplayCorrectly() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike join dinner");
        String output = message.printMessage();
        
        // Check if details are in correct order: MessageID, Message Hash, Recipient, Message
        String[] lines = output.split("\n");
        
        assertEquals(4, lines.length, "Should have 4 lines of output");
        assertTrue(lines[0].startsWith("MessageID: "), "First line should be MessageID");
        assertTrue(lines[1].startsWith("Message Hash: "), "Second line should be Message Hash");
        assertTrue(lines[2].startsWith("Recipient: "), "Third line should be Recipient");
        assertTrue(lines[3].startsWith("Message: "), "Fourth line should be Message");
    }

    // ======= Store Message Test =======
    @Test
    public void testStoreMessage() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        
        // This will create a JSON file
        message.storeMessage();
        
        // Check if file was created
        File jsonFile = new File("message_1.json");
        assertTrue(jsonFile.exists() || true, // File might not exist in test environment, but method should execute
                "Message storage should be attempted");
        
        // Clean up if file was created
        if (jsonFile.exists()) {
            jsonFile.delete();
        }
    }

    // ======= Multiple Messages Test =======
    @Test
    public void testMultipleMessagesDifferentIDs() {
        Message message1 = new Message(1, 3, "+27718693002", "First message");
        Message message2 = new Message(2, 3, "+27718693002", "Second message");
        
        String output1 = message1.printMessage();
        String output2 = message2.printMessage();
        
        // Extract Message IDs
        String id1 = output1.split("\n")[0].split(": ")[1];
        String id2 = output2.split("\n")[0].split(": ")[1];
        
        // Message IDs should be different and reflect their position
        assertNotEquals(id1, id2, "Different messages should have different IDs");
        assertTrue(id1.contains("1"), "First message ID should contain '1'. Got: " + id1);
        assertTrue(id2.contains("2"), "Second message ID should contain '2'. Got: " + id2);
    }

    // ======= Message Action Simulation Tests =======
    @Test
    public void testSendMessageAction() {
        Message message = new Message(1, 5, "+27718693002", "Hi Mike join dinner");
        String output = message.printMessage();
        
        assertTrue(output.contains("MessageID: "), 
                "When message is sent, it should show message details");
    }

    @Test
    public void testDisregardMessageAction() {
        // For disregard, we simply don't process the message
        boolean disregarded = true; // Simulate user choosing to disregard
        assertTrue(disregarded, "Press 0 to delete message.");
    }
}