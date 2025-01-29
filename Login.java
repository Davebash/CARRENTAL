package DemoTrail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Login extends CarRentalSystem {
    JButton loginBtn;
    Li li;

    public Login() {
        super("Hello");
        setTitle("Log In");
        setSize(300, 200);
        setIcon();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(3, 1));
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        l1 = new JLabel("Username:");
        l2 = new JLabel("Password:");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginBtn = new JButton("Login");
        p1.add(l1);
        p1.add(usernameField);
        p2.add(l2);
        p2.add(passwordField);
        p3.add(new JLabel());
        p3.add(loginBtn);
        add(panel);
        panel.add(p1);
        panel.add(p2);
        panel.add(p3);

        li = new Li(this);
        loginBtn.addActionListener(li);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class Li implements ActionListener {

        Login log;

        public Li(Login log) {
            this.log = log;
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == log.loginBtn) {
                if ( usernameField.getText().equalsIgnoreCase(getAdminUsername()) || usernameField.getText().equalsIgnoreCase(getManagerUsername()) ||usernameField.getText().equalsIgnoreCase(getEmployeeUsername())) {
                    char[] pf = passwordField.getPassword();
                    password = new String(pf);
                    if (usernameField.getText().equalsIgnoreCase(getAdminUsername()) && password.equals(getAdminPassword() )) {
                        log.dispose();
                        new AdminInterface();
                    } else if (usernameField.getText().equalsIgnoreCase(getManagerUsername()) && password.equals(getManagerPassword())) {
                        log.dispose();
                        new ManagerClass();
                    } else if (usernameField.getText().equalsIgnoreCase(getEmployeeUsername() ) && password.equals(getEmployeePassword())) {
                        log.dispose();
                        new EmployeeClass();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect Password", "Password", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Username", "Username", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
