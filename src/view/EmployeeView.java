package view;

import entity.User;

import javax.swing.*;

public class EmployeeView extends Layout{
    private JPanel container;
    private JLabel btn_employee_panel;
    private JLabel lbl_welcome;
    private JTabbedPane tabbedPane_hotel;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private User user;
    public EmployeeView(User user) {
        this.add(container);
        this.guiInitilaze(1000, 500);
    }
}
