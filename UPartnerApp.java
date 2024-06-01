import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class User {
    protected String name;
    protected String department;
    protected String email;
    protected String password;

    public User(String name, String department, String email, String password) {
        this.name = name;
        this.department = department;
        this.email = email;
        this.password = password;
    }
}

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
}

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
    public String getName() {
        return name;
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
}

class Project {
    private String dosenName;
    private String projectName;
    private String description;
    private String registrationDeadline;
    private String requirements;
    private String jobDesk;
    private String status;
    private String department;

    public Project(String dosenName, String projectName, String description, String registrationDeadline, String requirements, String department) {
        this.dosenName = dosenName;
        this.projectName = projectName;
        this.description = description;
        this.registrationDeadline = registrationDeadline;
        this.requirements = requirements;
        this.status = "Masa Pendaftaran";
        this.department = department;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getJobDesk() {
        return jobDesk;
    }

    public void setJobDesk(String jobDesk) {
        this.jobDesk = jobDesk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDosenName() {
        return dosenName;
    }

    public String getDepartment() {
        return department;
    }
}

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

public class UPartnerApp {
    private JFrame frame;
    private JPanel panel;
    private CardLayout cardLayout;

    private ArrayList<Mahasiswa> mahasiswaList;
    private ArrayList<Dosen> dosenList;
    private ArrayList<Project> projectList;

    public static void main(String[] args) {
        new UPartnerApp().init();
    }

    public UPartnerApp() {
        mahasiswaList = new ArrayList<>();
        dosenList = new ArrayList<>();
        projectList = new ArrayList<>();
    }

    public void init() {
        frame = new JFrame("UPartner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panel = new JPanel(cardLayout);

        panel.add(createWelcomePanel(), "welcome");
        panel.add(createRegisterPanel(), "register");
        panel.add(createLoginPanel(), "login");

        frame.add(panel);
        cardLayout.show(panel, "welcome");

        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME TO UPartner", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        registerButton.addActionListener(e -> cardLayout.show(panel, "register"));
        loginButton.addActionListener(e -> cardLayout.show(panel, "login"));

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        welcomePanel.add(buttonPanel, BorderLayout.SOUTH);

        return welcomePanel;
    }

    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BorderLayout());

        JLabel registerLabel = new JLabel("Register", SwingConstants.CENTER);
        registerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        registerPanel.add(registerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton mahasiswaButton = new JButton("Register as Mahasiswa");
        JButton dosenButton = new JButton("Register as Dosen");
        JButton backButton = new JButton("Back");

        mahasiswaButton.addActionListener(e -> registerMahasiswa());
        dosenButton.addActionListener(e -> registerDosen());
        backButton.addActionListener(e -> cardLayout.show(panel, "welcome"));

        buttonPanel.add(mahasiswaButton);
        buttonPanel.add(dosenButton);
        buttonPanel.add(backButton);

        registerPanel.add(buttonPanel, BorderLayout.CENTER);

        return registerPanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BorderLayout());

        JLabel loginLabel = new JLabel("Login", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Serif", Font.BOLD, 20));
        loginPanel.add(loginLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton mahasiswaButton = new JButton("Login as Mahasiswa");
        JButton dosenButton = new JButton("Login as Dosen");
        JButton backButton = new JButton("Back");

        mahasiswaButton.addActionListener(e -> loginMahasiswa());
        dosenButton.addActionListener(e -> loginDosen());
        backButton.addActionListener(e -> cardLayout.show(panel, "welcome"));

        buttonPanel.add(mahasiswaButton);
        buttonPanel.add(dosenButton);
        buttonPanel.add(backButton);

        loginPanel.add(buttonPanel, BorderLayout.CENTER);

        return loginPanel;
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
                if (mahasiswa.email.equals(email) && mahasiswa.password.equals(password)) {
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
                if (dosen.email.equals(email) && dosen.password.equals(password)) {
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
        searchProjectButton.addActionListener(e -> searchProject());
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
            "Registration Deadline:", registrationDeadlineField,
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

            if (projectName.isEmpty() || description.isEmpty() || registrationDeadline.isEmpty() || requirements.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Project project = new Project(dosen.getName(), projectName, description, registrationDeadline, requirements, department);
                projectList.add(project);
                dosen.uploadProject(project);
                JOptionPane.showMessageDialog(null, "Project uploaded successfully!");
            }
        }
    }

    private void searchProject() {
        JTextField departmentField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
            "Department:", departmentField,
            "Date:", dateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Search Project", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String department = departmentField.getText();
            String date = dateField.getText();

            ArrayList<Project> foundProjects = new ArrayList<>();
            for (Project project : projectList) {
                if (project.getStatus().equals("Masa Pendaftaran") && project.getDepartment().equalsIgnoreCase(department) && project.getRegistrationDeadline().equals(date)) {
                    foundProjects.add(project);
                }
            }

            if (foundProjects.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No projects found!");
            } else {
                StringBuilder result = new StringBuilder("Found Projects:\n");
                for (Project project : foundProjects) {
                    result.append("  - ").append(project.getProjectName()).append("\n");
                }
                JOptionPane.showMessageDialog(null, result.toString());
            }
        }
    }

    private void chat(String userType, String identifier) {
        JTextField recipientField = new JTextField();
        JTextField messageField = new JTextField();

        Object[] message = {
            "Recipient:", recipientField,
            "Message:", messageField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Chat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String recipient = recipientField.getText();
            String messageText = messageField.getText();
            String chatFileName = "chat_" + identifier + "_" + recipient + ".txt";

            ChatSystem chatSystem = new ChatSystem(chatFileName);
            chatSystem.sendMessage(userType + " " + identifier + ": " + messageText);
            JOptionPane.showMessageDialog(null, "Message sent!");

            int viewHistoryOption = JOptionPane.showConfirmDialog(null, "View chat history?", "Chat", JOptionPane.YES_NO_OPTION);
            if (viewHistoryOption == JOptionPane.YES_OPTION) {
                chatSystem.viewChatHistory();
            }
        }
    }
}
