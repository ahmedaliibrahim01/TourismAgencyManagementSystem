package view;

import entity.User;

import javax.swing.*;

public class UserManagementView extends Layout{

    private JPanel container;
    private User user;

    public UserManagementView(User user) {
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
    }
}
