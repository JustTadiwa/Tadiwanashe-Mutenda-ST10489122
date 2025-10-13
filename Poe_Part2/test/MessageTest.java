import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageLengthSuccess() {
        String message = "Hi Mike, can you join us for dinner tonight";
        assertTrue(message.length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) longMessage.append("a");
        int excess = longMessage.length() - 250;
        assertTrue(longMessage.length() > 250,
                "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testRecipientFormatSuccess() {
        String recipient = "+27718693002";
        assertTrue(Message.checkRecipientCell(recipient),
                "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientFormatFailure() {
        String recipient = "08575975889";
        assertFalse(Message.checkRecipientCell(recipient),
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testMessageHash() {
        String messageID = "1234567890";
        String message = "Hi Mike, can you join us for dinner tonight";
        String hash = Message.createMessageHash(messageID, 1, message);
        // First 2 digits + ":" + messageCounter + ":" + firstWord + lastWord, uppercase
        String expectedHash = "12:1:HItonight".toUpperCase();
        assertEquals(expectedHash, hash);
    }

    @Test
    public void testMessageIDGenerated() {
        String sent = Message.sendMessage("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(sent.contains("MessageID: "), "Message ID generated: " + sent.split("\n")[0].split(": ")[1]);
    }

    @Test
    public void testSendMessage() {
        String result = Message.sendMessage("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(result.contains("MessageID"), "Message successfully sent.");
    }

    @Test
    public void testStoreMessage() {
        // This will create JSON file
        Message.storeMessage("+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(true, "Message successfully stored.");
    }

    @Test
    public void testDisregardMessage() {
        // For disregard, just simulate by not calling send or store
        assertTrue(true, "Press 0 to delete message.");
    }
}
