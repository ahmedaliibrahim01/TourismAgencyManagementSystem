package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

/**
 * UserGUI provides an interface for managing user information.
 * This class presents an interface using Swing components for adding or updating user details.
 */
public class UserGUI extends Layout {

    private JPanel container;
    private JTextField txtf_username;
    private JTextField txtf_password;
    private JButton btn_user_save;
    private JLabel lbl_update_new;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_role;
    private JComboBox<String> cmbx_role;
    private JTextField txtf_name_surname;
    private JLabel lbl_name_surname;
    private UserManager userManager;
    private User user;
    private DefaultComboBoxModel<String> defaultComboBoxModel;

    /**
     * Constructor for UserGUI class.
     * Initializes necessary objects and creates the interface for managing user information.
     * @param user The user for whom the details are being managed (null if adding new user)
     */
    public UserGUI(User user){
        this.userManager = new UserManager();
        this.user = user;
        this.add(container);
        this.guiInitialize(300, 350);
        this.defaultComboBoxModel = new DefaultComboBoxModel<>();

        // If user object is provided, populate fields with user details
        if (user != null) {
            this.txtf_name_surname.setText(user.getFullName());
            this.txtf_username.setText(user.getUserName());
            this.txtf_password.setText(user.getPassword());
            this.cmbx_role.setToolTipText(user.getRole().toString());
        }

        // ActionListener for save button
        btn_user_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.txtf_username) || Helper.isFieldEmpty(this.txtf_password)) {
                Helper.showMsg("fill");
            } else {
                boolean result = true;
                if (this.user == null) {
                    // If adding new user, create User object and save it
                    User newUser = new User(txtf_name_surname.getText(), txtf_username.getText(), txtf_password.getText(), User.Role.valueOf((String) cmbx_role.getSelectedItem()));
                    result = this.userManager.save(newUser);
                } else {
                    // If updating existing user, update user details
                    this.user.setFullName(txtf_name_surname.getText());
                    this.user.setUserName(txtf_username.getText());
                    this.user.setPassword(txtf_password.getText());
                    this.user.setRole(User.Role.valueOf((String) cmbx_role.getSelectedItem()));
                    this.userManager.update(this.user);
                }
                // Show message based on operation result
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}
