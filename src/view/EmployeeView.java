package view;

import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeView extends Layout{
    private JPanel container;
    private JTabbedPane tabbedPane_hotel;
    private JButton btn_employee_logout;
    private JTable tbl_hotel;
    private JLabel lbl_employee_panel;
    private JLabel lbl_employee_welcome;
    private JPanel pnl_hotel;
    private JPanel pnl_hotel_filter;
    private JPanel pnl_hotel_table;
    private JScrollPane scrl_hotel;
    private JPanel pnl_hotel_view;
    private JPanel pnl_hotel_update;
    private JPanel pnl_hotel_delete;
    private JPanel pnl_hotel_add;
    private JButton btn_hotel_view;
    private JLabel lbl_hotel_view;
    private JButton btn_hotel_update;
    private JLabel lbl_hotel_update;
    private JButton btn_hotel_delete;
    private JLabel lbl_hotel_delete;
    private JButton btn_hotel_add;
    private JLabel lbl_hotel_add;
    private User user;
    public EmployeeView(User user) {
        this.add(container);
        this.guiInitilaze(1000, 500);
        btn_employee_logout.addActionListener(e -> {
            LoginView loginView = new LoginView();
            dispose();
        });
    }
}
