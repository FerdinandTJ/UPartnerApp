import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UPartnerApp {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel panel;

    private List<Mahasiswa> mahasiswaList;
    private List<Dosen> dosenList;
    private List<Project> projectList;

    public UPartnerApp() {
        mahasiswaList = new ArrayList<>();
        dosenList = new ArrayList<>();
        projectList = new ArrayList<>();
        loadMahasiswa();
        loadDosen();
        loadProjects();

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
        JTextField programStudiField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "NIM:", NIMField,
            "Name:", nameField,
            "Program Studi:", programStudiField,
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String NIM = NIMField.getText();
            String name = nameField.getText();
            String programStudi = programStudiField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (NIM.isEmpty() || name.isEmpty() || programStudi.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(null, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Mahasiswa mahasiswa = new Mahasiswa(NIM, name, programStudi, email, password);
            mahasiswaList.add(mahasiswa);
            saveMahasiswa();
            JOptionPane.showMessageDialog(null, "Mahasiswa registered successfully!");
        }
    }

    private void registerDosen() {
        JTextField NIPField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField programStudiField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "NIP:", NIPField,
            "Name:", nameField,
            "Program Studi:", programStudiField,
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register Dosen", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String NIP = NIPField.getText();
            String name = nameField.getText();
            String programStudi = programStudiField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (NIP.isEmpty() || name.isEmpty() || programStudi.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(null, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Dosen dosen = new Dosen(NIP, name, programStudi, email, password);
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
                if (mahasiswa.getEmail().equals(email) && mahasiswa.getPassword().equals(password)) {
                    showMahasiswaMenu(mahasiswa);
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
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
                if (dosen.getEmail().equals(email) && dosen.getPassword().equals(password)) {
                    showDosenMenu(dosen);
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMahasiswaMenu(Mahasiswa mahasiswa) {
        String[] options = {"View Profile", "Search Project", "Register for Project", "Chat with Dosen", "Logout"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null, "Choose an option", "Mahasiswa Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0:
                    mahasiswa.viewProfile();
                    break;
                case 1:
                    searchProject(mahasiswa);
                    break;
                case 2:
                    registerForProject(mahasiswa);
                    break;
                case 3:
                    chat("Mahasiswa", mahasiswa.getNIM());
                    break;
                case 4:
                    // Logout
                    break;
                default:
                    break;
            }
        } while (choice != 4);
    }

    private void showDosenMenu(Dosen dosen) {
        String[] options = {"View Profile", "Upload Project", "Chat with Mahasiswa", "Logout"};
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null, "Choose an option", "Dosen Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (choice) {
                case 0:
                    dosen.viewProfile();
                    break;
                case 1:
                    uploadProject(dosen);
                    break;
                case 2:
                    chat("Dosen", dosen.getNIP());
                    break;
                case 3:
                    // Logout
                    break;
                default:
                    break;
            }
        } while (choice != 3);
    }

    private void uploadProject(Dosen dosen) {
        JTextField projectNameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField registrationDeadlineField = new JTextField();
        JTextField requirementsField = new JTextField();
        JTextField programStudiField = new JTextField();

        Object[] message = {
            "Project Name:", projectNameField,
            "Description:", descriptionField,
            "Registration Deadline (yyyy/mm/dd):", registrationDeadlineField,
            "Requirements:", requirementsField,
            "Program Studi:", programStudiField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Upload Project", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String projectName = projectNameField.getText();
            String description = descriptionField.getText();
            String registrationDeadline = registrationDeadlineField.getText();
            String requirements = requirementsField.getText();
            String programStudi = programStudiField.getText();

            if (projectName.isEmpty() || description.isEmpty() || registrationDeadline.isEmpty() || requirements.isEmpty() || programStudi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidDate(registrationDeadline)) {
                JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Project project = new Project(dosen.getName(), projectName, description, registrationDeadline, requirements, programStudi);
            projectList.add(project);
            saveProjects();
            JOptionPane.showMessageDialog(null, "Project uploaded successfully!");
        }
    }

    private void searchProject(Mahasiswa mahasiswa) {
        JTextField programStudiField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
            "Program Studi:", programStudiField,
            "Date (yyyy/mm/dd):", dateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Search Projects", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String programStudi = programStudiField.getText();
            String date = dateField.getText();

            if (!isValidDate(date)) {
                JOptionPane.showMessageDialog(null, "Invalid date format! Please use yyyy/mm/dd.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StringBuilder result = new StringBuilder("Projects:\n");
            for (Project project : projectList) {
                if (project.getProgramStudi().equals(programStudi) && project.getRegistrationDeadline().equals(date)) {
                    result.append("  - ").append(project.getProjectName()).append(": ").append(project.getDescription()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, result.toString());
        }
    }

    private void registerForProject(Mahasiswa mahasiswa) {
        JTextField projectNameField = new JTextField();

        Object[] message = {
            "Project Name:", projectNameField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register for Project", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String projectName = projectNameField.getText();

            if (projectName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Project Name must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Project project : projectList) {
                if (project.getProjectName().equals(projectName)) {
                    mahasiswa.addCompletedProject(project);
                    JOptionPane.showMessageDialog(null, "Successfully registered for the project!");
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Project not found!", "Error", JOptionPane.ERROR_MESSAGE);
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
                        chatSystem.sendMessage("Mahasiswa: " + message);
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
                        chatSystem.sendMessage("Dosen: " + message);
                    }
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isValidDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void loadMahasiswa() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mahasiswa.dat"))) {
            mahasiswaList = (List<Mahasiswa>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            mahasiswaList = new ArrayList<>();
        }
    }

    private void saveMahasiswa() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mahasiswa.dat"))) {
            oos.writeObject(mahasiswaList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDosen() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dosen.dat"))) {
            dosenList = (List<Dosen>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            dosenList = new ArrayList<>();
        }
    }

    private void saveDosen() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dosen.dat"))) {
            oos.writeObject(dosenList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProjects() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("projects.dat"))) {
            projectList = (List<Project>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            projectList = new ArrayList<>();
        }
    }

    private void saveProjects() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("projects.dat"))) {
            oos.writeObject(projectList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UPartnerApp::new);
    }
}
