import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
