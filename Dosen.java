import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

class Dosen extends User {
  private String NIP;
  private ArrayList<Project> uploadedProjects;

  public Dosen(String NIP, String name, String department, String email, String password) {
      super(name, department, email, password);
      this.NIP = NIP;
      this.uploadedProjects = new ArrayList<>();
  }

  public String getNIP() {
      return NIP;
  }

  public void viewProfile() {
      StringBuilder profile = new StringBuilder("Profile:\n");
      profile.append("NIP: ").append(NIP).append("\n");
      profile.append("Name: ").append(name).append("\n");
      profile.append("Department: ").append(department).append("\n");
      profile.append("Uploaded Projects:\n");
      for (Project project : uploadedProjects) {
          profile.append("  - ").append(project.getProjectName()).append("\n");
      }
      JOptionPane.showMessageDialog(null, profile.toString());
  }

  public void uploadProject(Project project) {
      uploadedProjects.add(project);
  }

  @Override
  public String toString() {
      return NIP + "," + name + "," + department + "," + email + "," + password;
  }
}
