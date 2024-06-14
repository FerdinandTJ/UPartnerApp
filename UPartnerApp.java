import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public class UPartnerApp {
    private ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    private ArrayList<Dosen> dosenList = new ArrayList<>();
    private ArrayList<Project> projectList = new ArrayList<>();
    private ArrayList<Chat> chatList = new ArrayList<>(); 

    public UPartnerApp() {
        loadMahasiswaFromFile();
        loadDosenFromFile();
        loadProjectsFromFile();
        loadChatsFromFile(); 
        showLoginMenu();
    }

    private void showLoginMenu() {
        String[] options = {"Login", "Register"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "UPartnerApp",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showLogin();
        } else if (choice == 1) {
            showRegister();
        }
    }

    private void showLogin() {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            for (Mahasiswa mahasiswa : mahasiswaList) {
                if (mahasiswa.getEmail().equals(email) && mahasiswa.getPassword().equals(password)) {
                    mahasiswaMenu(mahasiswa);
                    return;
                }
            }

            for (Dosen dosen : dosenList) {
                if (dosen.getEmail().equals(email) && dosen.getPassword().equals(password)) {
                    dosenMenu(dosen);
                    return;
                }
            }

            JOptionPane.showMessageDialog(null, "Invalid email or password!");
        }
    }

    private void showRegister() {
        String[] options = {"Mahasiswa", "Dosen"};
        int choice = JOptionPane.showOptionDialog(null, "Register as", "Register",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            registerMahasiswa();
        } else if (choice == 1) {
            registerDosen();
        }
    }

    private void registerMahasiswa() {
        JTextField NIMField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "NIM:", NIMField,
            "Name:", nameField,
            "Department:", departmentField,
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Register Mahasiswa", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String NIM = NIMField.getText();
            String name = nameField.getText();
            String department = departmentField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format!");
                return;
            }

            Mahasiswa mahasiswa = new Mahasiswa(NIM, name, department, email, password);
            mahasiswaList.add(mahasiswa);
            saveMahasiswaToFile(mahasiswa);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        }
            showLoginMenu();
    }

    private void registerDosen() {
        JTextField NIPField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField departmentField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "NIP:", NIPField,
            "Name:", nameField,
            "Department:", departmentField,
            "Email:", emailField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Register Dosen", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String NIP = NIPField.getText();
            String name = nameField.getText();
            String department = departmentField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format!");
                return;
            }

            Dosen dosen = new Dosen(NIP, name, department, email, password);
            dosenList.add(dosen);
            saveDosenToFile(dosen);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        }
            showLoginMenu();
    }

    private void mahasiswaMenu(Mahasiswa mahasiswa) {
        Object[] options = {
            "View Profile",
            "Search Project",
            "Apply for Project",
            "View Applications",
            "Chat with Dosen",
            "Logout"
        };

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Welcome, " + mahasiswa.getName(), "Mahasiswa Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    viewProfile(mahasiswa);
                    break;
                case 1:
                    searchProject(mahasiswa);
                    break;
                case 2:
                    applyForProject(mahasiswa);
                    break;
                case 3:
                    viewApplications(mahasiswa);
                    break;
                case 4:
                    chatWithDosen(mahasiswa); // New chat feature
                    break;
                case 5:
                    return;
            }
        }
    }

    private void dosenMenu(Dosen dosen) {
        Object[] options = {
            "Upload Project",
            "Approve Applications",
            "Chat with Mahasiswa",
            "Logout"
        };

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Welcome, " + dosen.getName(), "Dosen Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    uploadProject(dosen);
                    break;
                case 1:
                    approveApplications(dosen);
                    break;
                case 2:
                    chatWithMahasiswa(dosen); // New chat feature
                    break;
                case 3:
                    return;
            }
        }
    }

    private void viewProfile(Mahasiswa mahasiswa) {
        StringBuilder profile = new StringBuilder();
        profile.append("NIM: ").append(mahasiswa.getNIM()).append("\n");
        profile.append("Name: ").append(mahasiswa.getName()).append("\n");
        profile.append("Department: ").append(mahasiswa.getDepartment()).append("\n");
        profile.append("Email: ").append(mahasiswa.getEmail()).append("\n");

        JOptionPane.showMessageDialog(null, profile.toString(), "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchProject(Mahasiswa mahasiswa) {
        StringBuilder projects = new StringBuilder("Available Projects:\n");
        for (Project project : projectList) {
            projects.append("Project Name: ").append(project.getProjectName()).append("\n");
            projects.append("Description: ").append(project.getDescription()).append("\n");
            projects.append("Deadline: ").append(project.getRegistrationDeadline()).append("\n");
            projects.append("Requirements: ").append(project.getRequirements()).append("\n");
            projects.append("Department: ").append(project.getDepartment()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, projects.toString(), "Available Projects", JOptionPane.INFORMATION_MESSAGE);
    }

    private void applyForProject(Mahasiswa mahasiswa) {
        String projectName = JOptionPane.showInputDialog("Enter the project name to apply for:");
        if (projectName != null) {
            for (Project project : projectList) {
                if (project.getProjectName().equalsIgnoreCase(projectName)) {
                    JTextField requirementsField = new JTextField();
                    JTextField reasonField = new JTextField();

                    Object[] fields = {
                        "Requirements:", requirementsField,
                        "Reason for applying:", reasonField
                    };

                    int option = JOptionPane.showConfirmDialog(null, fields, "Apply for Project", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String requirements = requirementsField.getText();
                        String reason = reasonField.getText();
                        ProjectApplication application = new ProjectApplication(mahasiswa, requirements, reason);
                        project.addApplication(application);
                        saveProjectToFile(project);
                        JOptionPane.showMessageDialog(null, "Application submitted successfully!");
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Project not found!");
        }
    }

    private void viewApplications(Mahasiswa mahasiswa) {
        StringBuilder applications = new StringBuilder("Your Applications:\n");
        for (Project project : projectList) {
            for (ProjectApplication application : project.getApplications()) {
                if (application.getMahasiswa().equals(mahasiswa)) {
                    applications.append("Project Name: ").append(project.getProjectName()).append("\n");
                    applications.append("Requirements: ").append(application.getRequirements()).append("\n");
                    applications.append("Reason: ").append(application.getReason()).append("\n");
                    applications.append("Status: ").append(application.isApproved() ? "Approved" : "Pending").append("\n\n");
                }
            }
        }
        JOptionPane.showMessageDialog(null, applications.toString(), "Your Applications", JOptionPane.INFORMATION_MESSAGE);
    }

    private void chatWithDosen(Mahasiswa mahasiswa) {
        String[] dosenNames = dosenList.stream().map(Dosen::getName).toArray(String[]::new);
        String dosenName = (String) JOptionPane.showInputDialog(null, "Select Dosen to chat with:", "Chat with Dosen",
                JOptionPane.PLAIN_MESSAGE, null, dosenNames, dosenNames[0]);
        
        if (dosenName != null) {
            for (Dosen dosen : dosenList) {
                if (dosen.getName().equals(dosenName)) {
                    showChatWindow(mahasiswa, dosen);
                    return;
                }
            }
        }
    }

    private void chatWithMahasiswa(Dosen dosen) {
        String[] mahasiswaNames = mahasiswaList.stream().map(Mahasiswa::getName).toArray(String[]::new);
        String mahasiswaName = (String) JOptionPane.showInputDialog(null, "Select Mahasiswa to chat with:", "Chat with Mahasiswa",
                JOptionPane.PLAIN_MESSAGE, null, mahasiswaNames, mahasiswaNames[0]);
        
        if (mahasiswaName != null) {
            for (Mahasiswa mahasiswa : mahasiswaList) {
                if (mahasiswa.getName().equals(mahasiswaName)) {
                    showChatWindow(dosen, mahasiswa);
                    return;
                }
            }
        }
    }

    private void showChatWindow(User sender, User receiver) {
        JTextArea chatArea = new JTextArea(20, 50);
        chatArea.setEditable(false);
        JTextField messageField = new JTextField(40);
        JButton sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(messageField);
        inputPanel.add(sendButton);
        panel.add(inputPanel, BorderLayout.SOUTH);

        JFrame frame = new JFrame("Chat between " + sender.getName() + " and " + receiver.getName());
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        updateChatArea(chatArea, sender, receiver);

        sendButton.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                Chat chat = new Chat(sender, receiver, message, new Date());
                chatList.add(chat);
                saveChatsToFile();
                updateChatArea(chatArea, sender, receiver);
                messageField.setText("");
            }
        });
    }

    private void updateChatArea(JTextArea chatArea, User sender, User receiver) {
        chatArea.setText("");
        for (Chat chat : chatList) {
            if ((chat.getSender().equals(sender) && chat.getReceiver().equals(receiver)) ||
                (chat.getSender().equals(receiver) && chat.getReceiver().equals(sender))) {
                chatArea.append(chat.getSender().getName() + ": " + chat.getMessage() + "\n");
            }
        }
    }

    private void uploadProject(Dosen dosen) {
        JTextField projectNameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField deadlineField = new JTextField();
        JTextField requirementsField = new JTextField();
        JTextField departmentField = new JTextField();

        Object[] fields = {
            "Project Name:", projectNameField,
            "Description:", descriptionField,
            "Deadline (dd/MM/yyyy):", deadlineField,
            "Requirements:", requirementsField,
            "Department:", departmentField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Upload Project", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String projectName = projectNameField.getText();
            String description = descriptionField.getText();
            String deadlineStr = deadlineField.getText();
            String requirements = requirementsField.getText();
            String department = departmentField.getText();

            try {
                Date deadline = new SimpleDateFormat("dd/mm/yyyy").parse(deadlineStr);
                Project project = new Project(projectName, description, deadline, requirements, department);
                projectList.add(project);
                saveProjectToFile(project);
                JOptionPane.showMessageDialog(null, "Project uploaded successfully!");
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Invalid date format!");
            }
        }
    }

    private void approveApplications(Dosen dosen) {
        for (Project project : projectList) {
            for (ProjectApplication application : project.getApplications()) {  
                if (application.getRequirements().contains(dosen.getDepartment())) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Approve application for project: " + project.getProjectName() + "\n" +
                                    "Mahasiswa: " + application.getMahasiswa().getName() + "\n" +
                                    "Reason: " + application.getReason(), "Approve Application",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        application.setApproved(true);
                        saveProjectToFile(project);
                    }
                }
            }
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$").matcher(email).matches();
    }

    private void saveMahasiswaToFile(Mahasiswa mahasiswa) {
        try (FileWriter writer = new FileWriter("mahasiswa.txt", true)) {
            writer.write(mahasiswa.getNIM() + "," + mahasiswa.getName() + "," + mahasiswa.getDepartment() + "," +
                    mahasiswa.getEmail() + "," + mahasiswa.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDosenToFile(Dosen dosen) {
        try (FileWriter writer = new FileWriter("dosen.txt", true)) {
            writer.write(dosen.getNIP() + "," + dosen.getName() + "," + dosen.getDepartment() + "," +
                    dosen.getEmail() + "," + dosen.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveProjectToFile(Project project) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("projects.ser"))) {
            oos.writeObject(projectList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveChatsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("chats.ser"))) {
            oos.writeObject(chatList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMahasiswaFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("mahasiswa.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Mahasiswa mahasiswa = new Mahasiswa(data[0], data[1], data[2], data[3], data[4]);
                mahasiswaList.add(mahasiswa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDosenFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("dosen.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Dosen dosen = new Dosen(data[0], data[1], data[2], data[3], data[4]);
                dosenList.add(dosen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProjectsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("projects.ser"))) {
            projectList = (ArrayList<Project>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadChatsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("chats.ser"))) {
            chatList = (ArrayList<Chat>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UPartnerApp::new);
    }
}

class Mahasiswa implements User {
    private String NIM;
    private String name;
    private String department;
    private String email;
    private String password;

    public Mahasiswa(String NIM, String name, String department, String email, String password) {
        this.NIM = NIM;
        this.name = name;
        this.department = department;
        this.email = email;
        this.password = password;
    }

    public String getNIM() {
        return NIM;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mahasiswa mahasiswa = (Mahasiswa) o;
        return NIM.equals(mahasiswa.NIM);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NIM);
    }
}

class Dosen implements User, Serializable {
    private String NIP;
    private String name;
    private String department;
    private String email;
    private String password;

    public Dosen(String NIP, String name, String department, String email, String password) {
        this.NIP = NIP;
        this.name = name;
        this.department = department;
        this.email = email;
        this.password = password;
    }

    public String getNIP() {
        return NIP;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dosen dosen = (Dosen) o;
        return NIP.equals(dosen.NIP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NIP);
    }
}

class Project implements Serializable {
    private String projectName;
    private String description;
    private Date registrationDeadline;
    private String requirements;
    private String department;
    private ArrayList<ProjectApplication> applications = new ArrayList<>();

    public Project(String projectName, String description, Date registrationDeadline, String requirements, String department) {
        this.projectName = projectName;
        this.description = description;
        this.registrationDeadline = registrationDeadline;
        this.requirements = requirements;
        this.department = department;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getDepartment() {
        return department;
    }

    public void addApplication(ProjectApplication application) {
        applications.add(application);
    }

    public ArrayList<ProjectApplication> getApplications() {
        return applications;
    }
}

class ProjectApplication implements Serializable {
    private Mahasiswa mahasiswa;
    private String requirements;
    private String reason;
    private boolean approved;

    public ProjectApplication(Mahasiswa mahasiswa, String requirements, String reason) {
        this.mahasiswa = mahasiswa;
        this.requirements = requirements;
        this.reason = reason;
        this.approved = false;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getReason() {
        return reason;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}

class Chat implements Serializable {
    private User sender;
    private User receiver;
    private String message;
    private Date timestamp;

    public Chat(User sender, User receiver, String message, Date timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

interface User extends Serializable {
    String getName();
}
