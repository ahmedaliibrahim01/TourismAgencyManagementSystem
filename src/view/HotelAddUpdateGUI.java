package view;

import business.FacilityManager;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HotelAddUpdateGUI extends Layout {
    private JPanel container;
    private JTextField txtf_hotel_name;
    private JTextField txtf_hotel_city;
    private JTextField txtf_hotel_region;
    private JTextField txtf_hotel_full_address;
    private JTextField txtf_hotel_email;
    private JTextField txtf_hotel_phone;
    private JTextField txtf_hotel_star;
    private JButton addButton;
    private JButton removeButton;
    private JButton addButton1;
    private JButton removeButton1;
    private JTable tbl_facilities;
    private JLabel lbl_hotel_add_update;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_reion;
    private JLabel lbl_hotel_full_address;
    private JLabel lbl_hotel_email;
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_star;
    private JPanel hotel_lbls;
    private JPanel hotel_txtfs;
    private JTabbedPane tabbedPane_unFclty;
    private JTabbedPane tabbedPane2;
    private JTable table2;
    private JTabbedPane tabbedPane3;
    private JTable table3;
    private JTabbedPane tabbedPane4;
    private JTable table4;
    private JPanel unf_panel;
    private JButton saveButton;
    private DefaultTableModel tmdl_users = new DefaultTableModel();
    private User user;
    private FacilityManager facilityManager;

    public HotelAddUpdateGUI(User user) {
        this.facilityManager = new FacilityManager();
        this.add(container);
        this.guiInitilaze(500, 720);
        container.setPreferredSize(new Dimension(1000, 720));
        this.user = user;
        if (this.user == null) {
            dispose();
        }

        // Facilities Management
        loadFacilityTable();
        loadFacilityComponent();

        // Pension Types Management
//        loadFacilitiesTable();
//        loadFacilitiesComponent();
        reComponent();
        printFrameSize(unf_panel);
    }

    public void loadFacilityTable() {
        Object[] col_facility_list = {"Facility Name"};
        ArrayList<Object[]> facilityList = this.facilityManager.getForTable(col_facility_list.length);
        this.createTable(this.tmdl_users, this.tbl_facilities, col_facility_list, facilityList);
    }

    public void loadFacilityComponent() {
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
    }

    public void reComponent() {
        this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_facilities.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();

    }
}
