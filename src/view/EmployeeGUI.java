package view;

import business.FacilityManager;
import business.HotelManager;
import business.PensionTypeManager;
import core.Helper;
import entity.Hotel;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EmployeeGUI extends Layout {
    private JPanel container;
    private JTabbedPane tabbedPane_hotel;
    private JButton btn_employee_logout;
    private JTable tbl_hotels;
    private JLabel lbl_employee_panel;
    private JLabel lbl_employee_welcome;
    private JPanel pnl_hotel;
    private JPanel pnl_hotel_table;
    private JScrollPane scrl_hotel;
    private JButton btn_hotel_update;
    private JLabel lbl_hotel_update;
    private JButton btn_hotel_delete;
    private JLabel lbl_hotel_delete;
    private JButton btn_hotel_add;
    private JLabel lbl_hotel_add;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTable tbl_facilities;
    private JTable tbl_pension_types;
    private JTable tbl_seasons;
    private JPanel pnl;
    private JPanel pnl_1;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JButton btn_add_update;
    private User user;
    private DefaultTableModel tmdl_hotels = new DefaultTableModel();
    DefaultTableModel tmdl_FacilitiesMdl = new DefaultTableModel();
    DefaultTableModel tmdl_PensionTypesMdl = new DefaultTableModel();

    private HotelManager hotelManager;
    private FacilityManager facilityManager;
    private Hotel hotel;
    private PensionTypeManager pensionTypeManager;

    public EmployeeGUI(User user) {
        this.hotel = new Hotel();
        this.facilityManager = new FacilityManager();
        this.pensionTypeManager = new PensionTypeManager();
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitilaze(1500, 1000);
        this.container.setPreferredSize(new Dimension(1500,1000));
        this.container.revalidate();
        this.container.repaint();

        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_employee_welcome.setText("Welcome Back :  " + Helper.firstWordUpper(this.user.getNameSurname()));
        if (this.hotel != null) {
            String[] facilities = hotel.getFacilities();
            if (facilities != null && facilities.length > 0) {
                StringBuilder tooltipText = new StringBuilder();
                for (String facility : facilities) {
                    tooltipText.append(facility).append("\n");
                }
                tbl_facilities.setToolTipText(tooltipText.toString());
            }

            String[] pensionTypes = hotel.getPensionTypes();
            if (pensionTypes != null && pensionTypes.length > 0) {
                StringBuilder tooltipText = new StringBuilder();
                for (String pensionType : pensionTypes) {
                    tooltipText.append(pensionType).append("\n");
                }
                tbl_pension_types.setToolTipText(tooltipText.toString());
            }

        }
        // Hotel Management
        loadHotelsTable();
        loadHotelComponent();
        reSizeComponent();

        // Features
        loadFacilityTable();
        loadPensionTypeTable();

        logout();
    }
    // Hotel Table
    private void loadHotelsTable() {
        Object[] col_hotel_list = {"ID", "Name", "City", "Region", "Full Address", "Phone", "Email", "Star"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel_list.length);
        this.createTable(this.tmdl_hotels, this.tbl_hotels, col_hotel_list, hotelList);
    }

    // Facility Table
    private void loadFacilityTable() {
        Object[] col_facility_list = {"Facility Name"};
        int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
        if (selectedHotelId != -1) {
            ArrayList<Object[]> facilityList = this.facilityManager.getForTableHotelFacility(col_facility_list.length, selectedHotelId);
            this.createTable(this.tmdl_FacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
            this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        } else {
            this.tmdl_FacilitiesMdl.setRowCount(0);
        }
    }

    // Pension Type Table
    private void loadPensionTypeTable(){
        Object[] col_pension_type_list = {"Pension Type Name"};
        int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
        if (selectedHotelId != -1){
            ArrayList<Object[]> pensionTypeList = this.pensionTypeManager.getForTableHotelPensions(col_pension_type_list.length, selectedHotelId);
            this.createTable(this.tmdl_PensionTypesMdl, this.tbl_pension_types,col_pension_type_list, pensionTypeList);
            this.tbl_pension_types.getColumnModel().getColumn(0).setMaxWidth(200);
        }else {
            this.tmdl_PensionTypesMdl.setRowCount(0);
        }
    }
    private void loadHotelComponent() {
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        btn_hotel_add.addActionListener(e -> {
            HotelAddGUI hotelAddUpdateGUI = new HotelAddGUI();
            hotelAddUpdateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelsTable();
                    reSizeComponent();
                }
            });
        });

        btn_hotel_update.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
            if (selectedHotelId != -1) {
                HotelUpdateGUI hotelUpdateGUI = new HotelUpdateGUI(this.hotelManager.getById(selectedHotelId));
                hotelUpdateGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelsTable();
                        reSizeComponent();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_hotel_delete.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels,0);
            if (selectedHotelId != -1) {
                if (Helper.confirm("sure","Delete")) {
                    if (this.hotelManager.delete(selectedHotelId)) {
                        Helper.showMsg("done");
                        loadHotelsTable();
                        reSizeComponent();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        tbl_hotels.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFacilityTable();
                loadPensionTypeTable();
            }
        });
    }
    public void logout() {
        btn_employee_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginView = new LoginGUI();
        });
    }
    public void reSizeComponent() {
        this.tbl_hotels.setPreferredSize(new Dimension(tbl_hotels.getWidth(), 585));
        this.tbl_hotels.revalidate();
        this.tbl_hotels.repaint();

        this.tbl_hotels.getColumnModel().getColumn(0).setMaxWidth(40);
        this.tbl_hotels.getColumnModel().getColumn(1).setMaxWidth(180);
        this.tbl_hotels.getColumnModel().getColumn(2).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(4).setMaxWidth(450);
        this.tbl_hotels.getColumnModel().getColumn(5).setMaxWidth(150);
        this.tbl_hotels.getColumnModel().getColumn(6).setMaxWidth(200);
        this.tbl_hotels.getColumnModel().getColumn(7).setMaxWidth(40);

        this.tbl_facilities.setPreferredSize(new Dimension(160, 142));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();


        this.tbl_pension_types.setPreferredSize(new Dimension(160, 142));
        this.tbl_pension_types.revalidate();
        this.tbl_pension_types.repaint();


        this.tbl_seasons.setPreferredSize(new Dimension(160, 142));
        this.tbl_seasons.revalidate();
        this.tbl_seasons.repaint();

        this.pnl_1.setPreferredSize(new Dimension(160, 142));
        this.tbl_seasons.revalidate();
        this.tbl_seasons.repaint();
    }
}