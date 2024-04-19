package view;

import business.FacilityManager;
import business.HotelManager;
import business.PensionTypeManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelAddGUI extends Layout {
    private JPanel container;
    private JTextField txtf_hotel_name;
    private JTextField txtf_hotel_city;
    private JTextField txtf_hotel_region;
    private JTextField txtf_hotel_full_address;
    private JTextField txtf_hotel_email;
    private JTextField txtf_hotel_phone;
    private JTextField txtf_hotel_star;
    private JButton btn_add_facility;
    private JButton btn_remove_facility;
    private JButton btn_add_pension;
    private JButton btn_remove_pension;
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
    private JTable tbl_uns_facility;
    private JTabbedPane tabbedPane3;
    private JTable tbl_pension_type;
    private JTabbedPane tabbedPane4;
    private JTable table4;
    private JPanel unf_panel;
    private JButton btn_save_hotel;
    private final Hotel hotel;
    private FacilityManager facilityManager;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {};
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};

    private final PensionTypeManager pensionTypeManager;
    private final HotelManager hotelManager;

    public HotelAddGUI() {
        this.hotel = new Hotel();
        this.facilityManager = new FacilityManager();
        this.hotelManager = new HotelManager();
        this.pensionTypeManager = new PensionTypeManager();
        this.add(container);
        this.guiInitilaze(500, 720);
        container.setPreferredSize(new Dimension(1000, 720));


        // Hotel Management
        loadHotelComponent();

        // Facilities Management
        loadLeftFacilityTable();
        loadRightFacilityTable();


        reSizeComponent();

    }

    private void loadLeftFacilityTable() {
        Object[] col_facility_list = {"Facility Name"};
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableFacilities(col_facility_list.length);
        this.createTable(this.unselectedFacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
    }

    private void loadRightFacilityTable() {
        selectedFacilitiesMdl.addColumn("Facility Name");
        for(String facility : selectedFacilities){
            selectedFacilitiesMdl.addRow(new Object[]{facility});
        }
        tbl_uns_facility.setModel(selectedFacilitiesMdl);
    }

    private void loadHotelComponent() {
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        btn_save_hotel.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.txtf_hotel_name)
                    || Helper.isFieldEmpty(this.txtf_hotel_city)
                    || Helper.isFieldEmpty(this.txtf_hotel_region)
                    || Helper.isFieldEmpty(this.txtf_hotel_full_address)
                    || Helper.isFieldEmpty(this.txtf_hotel_email)
                    || Helper.isFieldEmpty(this.txtf_hotel_phone)
                    || Helper.isFieldEmpty(this.txtf_hotel_star)
            || Helper.isTableEmpty(tbl_uns_facility)) {
                Helper.showMsg("fill");
            } else {
                Hotel hotel = new Hotel(
                        txtf_hotel_name.getText(),
                        txtf_hotel_city.getText(),
                        txtf_hotel_region.getText(),
                        txtf_hotel_full_address.getText(),
                        txtf_hotel_email.getText(),
                        txtf_hotel_phone.getText(),
                        txtf_hotel_star.getText(),
                        selectedFacilities
                );

                if (hotelManager.save(hotel)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
            for (String unselectedFacilities : unselectedFacilities){
                System.out.println(unselectedFacilities);
            }
        });

        btn_add_facility.addActionListener(e -> {
            int selectedRow = tbl_facilities.getSelectedRow();
            if (selectedRow != -1){
                Object[] rowData = {tbl_facilities.getValueAt(selectedRow,0)};
                selectedFacilitiesMdl.addRow(rowData);
                String facilityName = (String) rowData[0];
                selectedFacilities = addFacilityToArray(selectedFacilities, facilityName);
                unselectedFacilitiesMdl.removeRow(selectedRow);
            }
        });
        btn_remove_facility.addActionListener(e -> {
            int selectedRow = tbl_uns_facility.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = {tbl_uns_facility.getValueAt(selectedRow, 0)};
                unselectedFacilitiesMdl.addRow(rowData);
                String facilityName = (String) rowData[0];
                unselectedFacilities = addFacilityToArray(unselectedFacilities, facilityName);
                selectedFacilitiesMdl.removeRow(selectedRow);
                selectedFacilities = removeHotelFromArray(selectedFacilities, facilityName);
            }
        });

    }
    private String[] addFacilityToArray(String[] selectedFacilities, String facilityName) {
        String[] newFacilities = new String[selectedFacilities.length + 1];
        System.arraycopy(selectedFacilities, 0, newFacilities, 0, selectedFacilities.length);
        newFacilities[newFacilities.length - 1] = facilityName;
        return newFacilities;
    }

    private String[] removeHotelFromArray(String[] selectedFacilities, String facilityName) {
        String[] newFacilities = new String[selectedFacilities.length - 1];
        int index = 0;
        for (String facility : selectedFacilities) {
            if (!facility.equals(facilityName)) {
                newFacilities[index++] = facility;
            }
        }
        return newFacilities;
    }

    public void reSizeComponent() {
        this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_facilities.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();

        //this.tbl_uns_facility.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_uns_facility.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_uns_facility.revalidate();
        this.tbl_uns_facility.repaint();

        //this.tbl_pension_type.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_pension_type.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_pension_type.revalidate();
        this.tbl_pension_type.repaint();
    }
}
