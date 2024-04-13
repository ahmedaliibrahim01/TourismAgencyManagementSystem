package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class UserView extends Layout{

    private JPanel container;
    private JTextField txtf_username;
    private JTextField txtf_password;
    private JButton btn_user_save;
    private JLabel lbl_update_new;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_role;
    private JComboBox<String> cmbx_role;
    private UserManager userManager;
    private User user;
    private DefaultComboBoxModel defaultComboBoxModel;

    public UserView(User user){
        this.userManager = new UserManager();
        this.user = user;
        this.add(container);
        this.guiInitilaze(300, 300);
        this.defaultComboBoxModel = new DefaultComboBoxModel<>();

        if (user != null) {
            this.txtf_username.setText(user.getName());
            this.txtf_password.setText(user.getPassword());
        }
        btn_user_save.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.txtf_username) || Helper.isFieldEmpty(this.txtf_password)) {
                Helper.showMsg("fill");
            } else {
                boolean result = true;
                if (this.user == null) {
                    User obj = new User(txtf_username.getText(),txtf_password.getText(), (User.Role) this.cmbx_role.getSelectedItem());
                    result = this.userManager.save(obj);
                }else {
                    this.user.setName(txtf_username.getText());
                    this.user.setPassword(txtf_password.getText());
                    this.user.setRole(User.Role.valueOf((String) cmbx_role.getSelectedItem()));
                    this.userManager.update(this.user);
                }

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
