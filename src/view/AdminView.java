package view;

import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AdminView extends Layout{
    private JPanel container;
    private User user;
    public AdminView(User user) {
        this.add(container);
        this.guiInitilaze(1000, 500);
    }
}
