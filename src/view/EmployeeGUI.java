package view;

import business.HotelManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        this.guiInitilaze(1500, 1000);
        container.setPreferredSize(new Dimension(1500,1000));

        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_employee_welcome.setText("Welcome Back :  " + Helper.firstWordUpper(this.user.getName()));


        // Hotel Management
        loadHotelsTable();
        loadHotelComponent();



        logout();
    }
    private void loadHotelsTable() {
        Object[] col_hotel_list = {"ID", "Name", "City", "Region", "Full Address", "Phone", "Email", "Star"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel_list.length);
        this.createTable(this.tmdl_hotels, this.tbl_hotels, col_hotel_list, hotelList);
        reComponent();
    }

    private void loadHotelComponent() {
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
                reComponent();
            }
        });

        btn_hotel_add.addActionListener(e -> {
            HotelAddUpdateGUI hotelAddUpdateGUI = new HotelAddUpdateGUI(null);
            hotelAddUpdateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelsTable();
                }
            });
        });

        btn_hotel_update.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels,0);
            if (selectedHotelId != -1) {
                HotelAddUpdateGUI hotelAddUpdateGUI = new HotelAddUpdateGUI(this.hotelManager.getById(selectedHotelId));
                hotelAddUpdateGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelsTable();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_hotel_delete.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels,0);
            if (selectedHotelId != -1) {
                HotelAddUpdateGUI hotelAddUpdateGUI = new HotelAddUpdateGUI(this.hotelManager.getById(selectedHotelId));
                if (Helper.confirm("sure","Delete")) {
                    if (this.hotelManager.delete(selectedHotelId)) {
                        Helper.showMsg("done");
                        hotelAddUpdateGUI.dispose();
                        loadHotelsTable();
                    } else {
                        Helper.showMsg("error");
                    }
                }else {
                    hotelAddUpdateGUI.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

    }


    public void logout(){
        btn_employee_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginView = new LoginGUI();
        });
    }

    public void reComponent() {
        this.tbl_hotels.setPreferredSize(new Dimension(tbl_hotels.getWidth(), 500));
        this.tbl_hotels.getColumnModel().getColumn(0).setMaxWidth(40);
        this.tbl_hotels.getColumnModel().getColumn(1).setMaxWidth(180);
        this.tbl_hotels.getColumnModel().getColumn(2).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(4).setMaxWidth(450);
        this.tbl_hotels.getColumnModel().getColumn(5).setMaxWidth(150);
        this.tbl_hotels.getColumnModel().getColumn(6).setMaxWidth(200);
        this.tbl_hotels.getColumnModel().getColumn(7).setMaxWidth(40);

        this.tbl_hotels.setPreferredSize(new Dimension(tbl_hotels.getWidth(), 435));
        this.tbl_hotels.revalidate();
        this.tbl_hotels.repaint();

    }
}
