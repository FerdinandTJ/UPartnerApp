import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

class Mahasiswa extends User {
  private String NIM;
  private ArrayList<Project> completedProjects;

  public Mahasiswa(String NIM, String name, String department, String email, String password) {
      super(name, department, email, password);
      this.NIM = NIM;
      this.completedProjects = new ArrayList<>();
  }

  public String getNIM() {
      return NIM;
  }

  public void viewProfile() {
      StringBuilder profile = new StringBuilder("Profile:\n");
      profile.append("NIM: ").append(NIM).append("\n");
      profile.append("Name: ").append(name).append("\n");
      profile.append("Department: ").append(department).append("\n");
      profile.append("Completed Projects:\n");
      for (Project project : completedProjects) {
          profile.append("  - ").append(project.getProjectName()).append(": ").append(project.getJobDesk()).append("\n");
      }
      JOptionPane.showMessageDialog(null, profile.toString());
  }

  public void addCompletedProject(Project project) {
      completedProjects.add(project);
  }

  @Override
  public String toString() {
      return NIM + "," + name + "," + department + "," + email + "," + password;
  }
}
