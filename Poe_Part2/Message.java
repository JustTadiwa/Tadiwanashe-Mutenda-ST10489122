import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    private static int messageCounter = 0; // Tracks number of messages sent
    private static ArrayList<String> sentMessages = new ArrayList<>(); // Stores messages for printing

    // === VALIDATE MESSAGE ID ===
    public static boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    // === VALIDATE RECIPIENT CELL NUMBER ===
    public static boolean checkRecipientCell(String recipient) {
        return recipient.matches("^\\+[0-9]{11}$");
    }

    // CREATE MESSAGE HASH
    // Format: first 2 of messageID : message number : first word + last word
    public static String createMessageHash(String messageID, int num, String message) {
        String[] words = message.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return (messageID.substring(0, 2) + ":" + num + ":" + firstWord + lastWord).toUpperCase();
    }

    // === SEND MESSAGE ===
    public static String sendMessage(String recipient, String message) {
        messageCounter++;

        // Generate 10-digit random message ID
        String messageID = String.valueOf((long)(Math.random() * 9_000_000_000L + 1_000_000_000L));
        String messageHash = createMessageHash(messageID, messageCounter, message);

        // Build message details for printing
        String msgDetails = "MessageID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + message +
                "\n-----";

        sentMessages.add(msgDetails);
        return msgDetails;
    }

    // STORE MESSAGE TO JSON FILE
    public static void storeMessage(String recipient, String message) {
        JSONObject obj = new JSONObject();
        obj.put("Recipient", recipient);
        obj.put("Message", message);

        JSONArray arr = new JSONArray();
        arr.add(obj);

        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(arr.toJSONString());
            file.flush();
            System.out.println("Message stored in JSON file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  PRINT ALL MESSAGES SENT
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (String msg : sentMessages) {
            sb.append(msg).append("\n");
        }
        sb.append("\nTotal messages sent during this run: ").append(messageCounter);
        return sb.toString();
    }

    // RETURN TOTAL NUMBER OF MESSAGES SENT
    public static int returnTotalMessages() {
        return messageCounter;
    }
}
