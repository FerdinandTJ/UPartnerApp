import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

class ChatSystem {
  private String chatFileName;

  public ChatSystem(String chatFileName) {
      this.chatFileName = chatFileName;
  }

  public void sendMessage(String message) {
      try (FileWriter fw = new FileWriter(chatFileName, true);
           BufferedWriter bw = new BufferedWriter(fw);
           PrintWriter out = new PrintWriter(bw)) {
          out.println(message);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  public void viewChatHistory() {
      try (BufferedReader br = new BufferedReader(new FileReader(chatFileName))) {
          StringBuilder chatHistory = new StringBuilder();
          String line;
          while ((line = br.readLine()) != null) {
              chatHistory.append(line).append("\n");
          }
          JOptionPane.showMessageDialog(null, chatHistory.toString());
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
