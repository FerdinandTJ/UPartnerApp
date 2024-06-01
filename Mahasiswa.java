import javax.swing.*;

public class Mahasiswa extends User {
  private String NIM;
  private String programStudi;

  public Mahasiswa(String NIM, String name, String programStudi, String email, String password) {
      super(name, email, password);
      this.NIM = NIM;
      this.programStudi = programStudi;
  }

  public String getNIM() {
      return NIM;
  }

  public String getProgramStudi() {
      return programStudi;
  }
  public String getPassword() {
    return super.getPassword();
}
  

  public void viewProfile() {
      StringBuilder profile = new StringBuilder();
      profile.append("NIM: ").append(NIM).append("\n");
      profile.append("Name: ").append(getName()).append("\n");
      profile.append("Program Studi: ").append(programStudi).append("\n");
      profile.append("Projects Completed:\n");
      for (Project project : getCompletedProjects()) {
          profile.append("  - ").append(project.getProjectName()).append(": ").append(project.getDescription()).append("\n");
      }
      JOptionPane.showMessageDialog(null, profile.toString(), "Profile", JOptionPane.INFORMATION_MESSAGE);
  }
}
