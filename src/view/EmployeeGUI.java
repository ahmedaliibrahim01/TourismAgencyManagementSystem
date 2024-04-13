package view;

import business.HotelManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class EmployeeGUI extends Layout{
    private JPanel container;
    private JTabbedPane tabbedPane_hotel;
    private JButton btn_employee_logout;
    private JTable tbl_hotels;
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
    private DefaultTableModel tmdl_hotels = new DefaultTableModel();

    private HotelManager hotelManager;
    public EmployeeGUI(User user) {
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitilaze(1000, 500);

        // Hotel Management
        loadHotelsTable();
        loadUserComponent();


        logout();
    }
    private void loadHotelsTable() {
        Object[] col_hotel_list = {"ID", "Name"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel_list.length);
        this.createTable(this.tmdl_hotels, this.tbl_hotels, col_hotel_list, hotelList);
    }

    private void loadUserComponent() {
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
    }
    public void logout(){
        btn_employee_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginView = new LoginGUI();
        });
    }

}
