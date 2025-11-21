import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Message {
    // Instance variables - each Message object has its own data
    private String messageID;
    private String messageHash;
    private String recipient;
    private String messageText;
    private int messageNumber;

    // Constructor - creates a new Message object
    public Message(int messageNumber, int totalMessages, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        // Create Message ID using string manipulation (substring) and loop counter
        String baseID = "Msg" + this.messageNumber + "of" + totalMessages;
        // Use substring to ensure it's 10 characters or less
        if (baseID.length() > 10) {
            this.messageID = baseID.substring(0, 10);
        } else {
            this.messageID = baseID;
        }

        // Create hash using string manipulation
        this.messageHash = createMessageHash();
    }

    // Create message hash using string manipulation
    private String createMessageHash() {
        String[] words = this.messageText.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        // Uses substring on the messageID and loop counter for message number
        return (this.messageID.substring(0, 2) + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // Display message details in correct order
    public String printMessage() {
        return "MessageID: " + this.messageID +
                "\nMessage Hash: " + this.messageHash +
                "\nRecipient: " + this.recipient +
                "\nMessage: " + this.messageText;
    }

    // Store message as JSON file (manual JSON creation)
    public void storeMessage() {
        // Manually create JSON string without external libraries
        String jsonString = "{\n  \"messages\": [\n    {\n      \"Recipient\": \"" +
                this.recipient + "\",\n      \"Message\": \"" +
                this.messageText + "\"\n    }\n  ]\n}";

        try (FileWriter file = new FileWriter("message_" + this.messageNumber + ".json")) {
            file.write(jsonString);
            file.flush();
            JOptionPane.showMessageDialog(null, "Message stored in JSON file: message_" + this.messageNumber + ".json");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
    }

    // PART 3: ADDED GETTER METHODS
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public int getMessageLength() {
        return messageText.length();
    }

    // Method to display basic info (for reports)
    public String getBasicInfo() {
        return "To: " + recipient + " | Message: " +
                (messageText.length() > 30 ? messageText.substring(0, 30) + "..." : messageText);
    }
}