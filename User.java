import java.util.ArrayList;
import java.util.List;

public abstract class User implements UserInterface {
    private String name;
    private String email;
    private String password;
    private List<Project> completedProjects;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.completedProjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }

    public List<Project> getCompletedProjects() {
        return completedProjects;
    }

    public void addCompletedProject(Project project) {
        completedProjects.add(project);
    }

    public abstract void viewProfile();
}