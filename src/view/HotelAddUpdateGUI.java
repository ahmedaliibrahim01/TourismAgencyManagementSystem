package view;

import business.HotelManager;
import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class HotelAddUpdateGUI extends Layout{
    private JPanel container;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTabbedPane tabbedPane4;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private User user;
    private HotelManager hotelManager;

    public HotelAddUpdateGUI(User user) {
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitilaze(500, 500);
        container.setPreferredSize(new Dimension(1000,500));
        this.user = user;
        if (this.user == null) {
            dispose();
        }
    }
}
