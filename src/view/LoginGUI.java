package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

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


    public LoginGUI(){
        this.userManager = new UserManager();
        add(container);
        this.guiInitilaze(400, 400);

        btn_login.addActionListener(e -> {
            if (Helper.isFieldEmpty(textField_username)){
                Helper.showMsg("Enter Username");
            }else if (Helper.isFieldEmpty(passwordField)){
                Helper.showMsg("Enter Password");
            } else {
                User loginUser = this.userManager.findByLogin(this.textField_username.getText(),this.passwordField.getText());
                if (loginUser == null){
                    Helper.showMsg("User not found");
                }else {
                    if (loginUser.getRole().toString().equals("Admin") || loginUser.getRole().toString().equals("ADMIN") || loginUser.getRole().toString().equals("admin")) {
                        UserManagementGUI adminView = new UserManagementGUI(loginUser);
                        adminView.setVisible(true);
                        dispose();
                    } else {
                        EmployeeGUI adminView = new EmployeeGUI(loginUser);
                        adminView.setVisible(true);
                        dispose();
                    }
                }
            }
        });
    }
    }
