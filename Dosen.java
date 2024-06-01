import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Dosen extends User {
  private String NIP;
  private String programStudi;
  private List<Project> projectList;

  public Dosen(String NIP, String name, String programStudi, String email, String password) {
      super(name, email, password);
      this.NIP = NIP;
      this.programStudi = programStudi;
      this.projectList = new ArrayList<>();
  }

  public String getNIP() {
      return NIP;
  }

  public String getProgramStudi() {
      return programStudi;
  }
  
  public String getPassword() {
    return super.getPassword();
}

  public void viewProfile() {
      StringBuilder profile = new StringBuilder();
      profile.append("NIP: ").append(NIP).append("\n");
      profile.append("Name: ").append(getName()).append("\n");
      profile.append("Program Studi: ").append(programStudi).append("\n");
      profile.append("Projects:\n");
      for (Project project : getCompletedProjects()) {
          profile.append("  - ").append(project.getProjectName()).append(": ").append(project.getDescription()).append("\n");
      }
      JOptionPane.showMessageDialog(null, profile.toString(), "Profile", JOptionPane.INFORMATION_MESSAGE);
  }

  public void uploadProject(Project project) {
    // Implementation to upload project
    boolean isDuplicate = false;
    for (Project p : projectList) {
        if (p.getProjectName().equals(project.getProjectName())) {
            isDuplicate = true;
            break;
        }
    }

    if (isDuplicate) {
        JOptionPane.showMessageDialog(null, "Project with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (project.getDosenName().isEmpty() || project.getProjectName().isEmpty() || project.getDescription().isEmpty()
            || project.getRegistrationDeadline().isEmpty() || project.getRequirements().isEmpty()) {
        JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    projectList.add(project);
    saveProjects();
    JOptionPane.showMessageDialog(null, "Project uploaded successfully!");
}

private void saveProjects() {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("projects.dat"))) {
        oos.writeObject(projectList);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error saving projects!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
  }

