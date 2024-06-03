import java.io.*;
import javax.swing.JOptionPane;

public class ChatSystem implements ChatInterface {
    private String chatFilePath;

    public ChatSystem(String chatFilePath) {
        this.chatFilePath = chatFilePath;
    }

    public void viewChatHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(chatFilePath))) {
            String line;
            StringBuilder chatHistory = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                chatHistory.append(line).append("\n");
            }
            JOptionPane.showMessageDialog(null, chatHistory.toString(), "Chat History", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading chat history", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chatFilePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error sending message", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}