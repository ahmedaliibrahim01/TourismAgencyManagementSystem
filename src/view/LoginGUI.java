package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

/**
 * LoginGUI provides an interface for user login.
 * This class presents an interface using Swing components for user authentication.
 */
public class LoginGUI extends Layout {

    private JPanel container;
    private JPanel user_login;
    private JPanel pnl_top;
    private JLabel lbl_rent_a_car;
    private JLabel lbl_management;
    private JPanel pnl_bottom;
    private JTextField textField_username;
    private JPasswordField passwordField;
    private JButton btn_login;
    private JLabel lbl_pass;
    private JLabel lbl_user;
    private final UserManager userManager;

    /**
     * Constructor for LoginGUI class.
     * Initializes necessary objects and creates the login interface.
     */
    public LoginGUI() {
        this.userManager = new UserManager();
        add(container);
        this.guiInitialize(400, 400);

        // Action listener for login button
        btn_login.addActionListener(e -> {
            // Check if username field is empty
            if (Helper.isFieldEmpty(textField_username)) {
                Helper.showMsg("Enter Username");
            }
            // Check if password field is empty
            else if (Helper.isFieldEmpty(passwordField)) {
                Helper.showMsg("Enter Password");
            } else {
                // Find user by login credentials
                User loginUser = this.userManager.findByLogin(this.textField_username.getText(), this.passwordField.getText());
                if (loginUser == null) {
                    Helper.showMsg("User not found");
                } else {
                    // Check user role and open respective GUI
                    if (loginUser.getRole().toString().equalsIgnoreCase("Admin")) {
                        UserManagementGUI userManagementGUI = new UserManagementGUI(loginUser);
                        userManagementGUI.setVisible(true);
                        dispose();
                    } else {
                        EmployeeGUI employeeGUI = new EmployeeGUI(loginUser);
                        employeeGUI.setVisible(true);
                        dispose();
                    }
                }
            }
        });
    }
}
