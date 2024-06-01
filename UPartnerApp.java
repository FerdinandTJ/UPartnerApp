import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class UPartnerApp {
  private JFrame frame;
  private JPanel panel;
  private CardLayout cardLayout;

  private ArrayList<Mahasiswa> mahasiswaList;
  private ArrayList<Dosen> dosenList;
  private ArrayList<Project> projectList;

  private static final String MAHASISWA_FILE = "mahasiswa.txt";
  private static final String DOSEN_FILE = "dosen.txt";
  private static final String PROJECT_FILE = "projects.txt";

  public static void main(String[] args) {
      new UPartnerApp().init();
  }

  public UPartnerApp() {
      mahasiswaList = new ArrayList<>();
      dosenList = new ArrayList<>();
      projectList = new ArrayList<>();
      loadData();
  }

  private void loadData() {
      loadMahasiswa();
      loadDosen();
      loadProjects();
  }

  private void loadMahasiswa() {
      try (BufferedReader br = new BufferedReader(new FileReader(MAHASISWA_FILE))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] data = line.split(",");
              Mahasiswa mahasiswa = new Mahasiswa(data[0], data[1], data[2], data[3], data[4]);
              mahasiswaList.add(mahasiswa);
          }
      } catch (IOException e) {
          // Ignore if file doesn't exist
      }
  }

  private void loadDosen() {
      try (BufferedReader br = new BufferedReader(new FileReader(DOSEN_FILE))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] data = line.split(",");
              Dosen dosen = new Dosen(data[0], data[1], data[2], data[3], data[4]);
              dosenList.add(dosen);
          }
      } catch (IOException e) {
          // Ignore if file doesn't exist
      }
  }

  private void loadProjects() {
      try (BufferedReader br = new BufferedReader(new FileReader(PROJECT_FILE))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] data = line.split(",");
              Project project = new Project(data[0], data[1], data[2], data[3], data[4], data[5]);
              projectList.add(project);
          }
      } catch (IOException e) {
          // Ignore if file doesn't exist
      }
  }

  private void saveMahasiswa() {
      try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(MAHASISWA_FILE)))) {
          for (Mahasiswa mahasiswa : mahasiswaList) {
              out.println(mahasiswa.toString());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  private void saveDosen() {
      try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DOSEN_FILE)))) {
          for (Dosen dosen : dosenList) {
              out.println(dosen.toString());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  private void saveProjects() {
      try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(PROJECT_FILE)))) {
          for (Project project : projectList) {
              out.println(project.toString());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

  public void init() {
      frame = new JFrame("UPartner");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 400);

      cardLayout = new CardLayout();
      panel = new JPanel(cardLayout);

      JPanel welcomePanel = new JPanel(new BorderLayout());
      JLabel welcomeLabel = new JLabel("WELCOME TO UPartner", SwingConstants.CENTER);
      welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
      welcomePanel.add(welcomeLabel, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel();
      JButton registerButton = new JButton("Register");
      JButton loginButton = new JButton("Login");
      buttonPanel.add(registerButton);
      buttonPanel.add(loginButton);
      welcomePanel.add(buttonPanel, BorderLayout.CENTER);

      panel.add(welcomePanel, "welcome");

      registerButton.addActionListener(e -> showRegisterPanel());
      loginButton.addActionListener(e -> showLoginPanel());

      frame.add(panel);
      frame.setVisible(true);
  }

  private void showRegisterPanel() {
      String[] options = {"Register as Mahasiswa", "Register as Dosen"};
      int choice = JOptionPane.showOptionDialog(null, "Choose registration type", "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
      if (choice == 0) {
          registerMahasiswa();
      } else if (choice == 1) {
          registerDosen();
      }
  }

  private void showLoginPanel() {
      String[] options = {"Login as Mahasiswa", "Login as Dosen"};
      int choice = JOptionPane.showOptionDialog(null, "Choose login type", "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
      if (choice == 0) {
          loginMahasiswa();
      } else if (choice == 1) {
          loginDosen();
      }
  }

  private void registerMahasiswa() {
      JTextField NIMField = new JTextField();
      JTextField nameField = new JTextField();
      JTextField departmentField = new JTextField();
      JTextField emailField = new JTextField();
      JPasswordField passwordField = new JPasswordField();

      Object[] message = {
          "NIM:", NIMField,
          "Name:", nameField,
          "Department:", departmentField,
          "Email:", emailField,
          "Password:", passwordField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Register Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String NIM = NIMField.getText();
          String name = nameField.getText();
          String department = departmentField.getText();
          String email = emailField.getText();
          String password = new String(passwordField.getPassword());

          Mahasiswa mahasiswa = new Mahasiswa(NIM, name, department, email, password);
          mahasiswaList.add(mahasiswa);
          saveMahasiswa();
          JOptionPane.showMessageDialog(null, "Mahasiswa registered successfully!");
      }
  }

  private void registerDosen() {
      JTextField NIPField = new JTextField();
      JTextField nameField = new JTextField();
      JTextField departmentField = new JTextField();
      JTextField emailField = new JTextField();
      JPasswordField passwordField = new JPasswordField();

      Object[] message = {
          "NIP:", NIPField,
          "Name:", nameField,
          "Department:", departmentField,
          "Email:", emailField,
          "Password:", passwordField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Register Dosen", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String NIP = NIPField.getText();
          String name = nameField.getText();
          String department = departmentField.getText();
          String email = emailField.getText();
          String password = new String(passwordField.getPassword());

          Dosen dosen = new Dosen(NIP, name, department, email, password);
          dosenList.add(dosen);
          saveDosen();
          JOptionPane.showMessageDialog(null, "Dosen registered successfully!");
      }
  }

  private void loginMahasiswa() {
      JTextField emailField = new JTextField();
      JPasswordField passwordField = new JPasswordField();

      Object[] message = {
          "Email:", emailField,
          "Password:", passwordField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Login Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String email = emailField.getText();
          String password = new String(passwordField.getPassword());

          for (Mahasiswa mahasiswa : mahasiswaList) {
              if (mahasiswa.getEmail().equals(email) && mahasiswa.password.equals(password)) {
                  showMahasiswaPanel(mahasiswa);
                  return;
              }
          }

          JOptionPane.showMessageDialog(null, "Invalid email or password!");
      }
  }

  private void loginDosen() {
      JTextField emailField = new JTextField();
      JPasswordField passwordField = new JPasswordField();

      Object[] message = {
          "Email:", emailField,
          "Password:", passwordField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Login Dosen", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String email = emailField.getText();
          String password = new String(passwordField.getPassword());

          for (Dosen dosen : dosenList) {
              if (dosen.getEmail().equals(email) && dosen.password.equals(password)) {
                  showDosenPanel(dosen);
                  return;
              }
          }

          JOptionPane.showMessageDialog(null, "Invalid email or password!");
      }
  }

  private void showMahasiswaPanel(Mahasiswa mahasiswa) {
      JPanel mahasiswaPanel = new JPanel(new BorderLayout());

      JLabel welcomeLabel = new JLabel("Welcome, " + mahasiswa.name, SwingConstants.CENTER);
      welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
      mahasiswaPanel.add(welcomeLabel, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel();
      JButton profileButton = new JButton("Lihat Profil");
      JButton searchProjectButton = new JButton("Cari Projek");
      JButton chatButton = new JButton("Chat Dosen");
      JButton logoutButton = new JButton("Keluar");

      profileButton.addActionListener(e -> mahasiswa.viewProfile());
      searchProjectButton.addActionListener(e -> searchProject(mahasiswa));
      chatButton.addActionListener(e -> chat("Mahasiswa", mahasiswa.getNIM()));
      logoutButton.addActionListener(e -> cardLayout.show(panel, "welcome"));

      buttonPanel.add(profileButton);
      buttonPanel.add(searchProjectButton);
      buttonPanel.add(chatButton);
      buttonPanel.add(logoutButton);

      mahasiswaPanel.add(buttonPanel, BorderLayout.CENTER);

      panel.add(mahasiswaPanel, "mahasiswa");
      cardLayout.show(panel, "mahasiswa");
  }

  private void showDosenPanel(Dosen dosen) {
      JPanel dosenPanel = new JPanel(new BorderLayout());

      JLabel welcomeLabel = new JLabel("Welcome, " + dosen.name, SwingConstants.CENTER);
      welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
      dosenPanel.add(welcomeLabel, BorderLayout.NORTH);

      JPanel buttonPanel = new JPanel();
      JButton profileButton = new JButton("Lihat Profil");
      JButton uploadProjectButton = new JButton("Upload Project");
      JButton chatButton = new JButton("Chat Mahasiswa");
      JButton logoutButton = new JButton("Keluar");

      profileButton.addActionListener(e -> dosen.viewProfile());
      uploadProjectButton.addActionListener(e -> uploadProject(dosen));
      chatButton.addActionListener(e -> chat("Dosen", dosen.getNIP()));
      logoutButton.addActionListener(e -> cardLayout.show(panel, "welcome"));

      buttonPanel.add(profileButton);
      buttonPanel.add(uploadProjectButton);
      buttonPanel.add(chatButton);
      buttonPanel.add(logoutButton);

      dosenPanel.add(buttonPanel, BorderLayout.CENTER);

      panel.add(dosenPanel, "dosen");
      cardLayout.show(panel, "dosen");
  }

  private void uploadProject(Dosen dosen) {
      JTextField projectNameField = new JTextField();
      JTextField descriptionField = new JTextField();
      JTextField registrationDeadlineField = new JTextField();
      JTextField requirementsField = new JTextField();
      JTextField departmentField = new JTextField();

      Object[] message = {
          "Project Name:", projectNameField,
          "Description:", descriptionField,
          "Registration Deadline (yyyy/mm/dd):", registrationDeadlineField,
          "Requirements:", requirementsField,
          "Department:", departmentField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Upload Project", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String projectName = projectNameField.getText();
          String description = descriptionField.getText();
          String registrationDeadline = registrationDeadlineField.getText();
          String requirements = requirementsField.getText();
          String department = departmentField.getText();

          if (!isValidDate(registrationDeadline)) {
              JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
          }

          if (projectName.isEmpty() || description.isEmpty() || registrationDeadline.isEmpty() || requirements.isEmpty() || department.isEmpty()) {
              JOptionPane.showMessageDialog(null, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
          } else {
              Project project = new Project(dosen.getName(), projectName, description, registrationDeadline, requirements, department);
              projectList.add(project);
              dosen.uploadProject(project);
              saveProjects();
              JOptionPane.showMessageDialog(null, "Project uploaded successfully!");
          }
      }
  }

  private boolean isValidDate(String dateStr) {
      try {
          DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
          df.setLenient(false);
          df.parse(dateStr);
          return true;
      } catch (ParseException e) {
          return false;
      }
  }

  private void searchProject(Mahasiswa mahasiswa) {
      JTextField departmentField = new JTextField();
      JTextField dateField = new JTextField();

      Object[] message = {
          "Department:", departmentField,
          "Date (yyyy/mm/dd):", dateField
      };

      int option = JOptionPane.showConfirmDialog(null, message, "Search Projects", JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
          String department = departmentField.getText();
          String date = dateField.getText();

          if (!isValidDate(date)) {
              JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
              return;
          }

          StringBuilder result = new StringBuilder("Projects:\n");
          for (Project project : projectList) {
              if (project.getDepartment().equals(department) && project.getRegistrationDeadline().equals(date)) {
                  result.append("  - ").append(project.getProjectName()).append(": ").append(project.getDescription()).append("\n");
              }
          }
          JOptionPane.showMessageDialog(null, result.toString());
      }
  }

  private void chat(String userType, String identifier) {
      String chatWith = JOptionPane.showInputDialog("Enter name of person to chat with:");

      if (userType.equals("Mahasiswa")) {
          for (Dosen dosen : dosenList) {
              if (dosen.getName().equals(chatWith)) {
                  ChatSystem chatSystem = new ChatSystem("chat_" + identifier + "_" + dosen.getNIP() + ".txt");
                  chatSystem.viewChatHistory();
                  String message = JOptionPane.showInputDialog("Enter your message:");
                  if (message != null && !message.trim().isEmpty()) {
                      chatSystem.sendMessage("Mahasiswa " + identifier + ": " + message);
                  }
                  return;
              }
          }
      } else if (userType.equals("Dosen")) {
          for (Mahasiswa mahasiswa : mahasiswaList) {
              if (mahasiswa.getName().equals(chatWith)) {
                  ChatSystem chatSystem = new ChatSystem("chat_" + identifier + "_" + mahasiswa.getNIM() + ".txt");
                  chatSystem.viewChatHistory();
                  String message = JOptionPane.showInputDialog("Enter your message:");
                  if (message != null && !message.trim().isEmpty()) {
                      chatSystem.sendMessage("Dosen " + identifier + ": " + message);
                  }
                  return;
              }
          }
      }

      JOptionPane.showMessageDialog(null, "User not found!");
  }
}
