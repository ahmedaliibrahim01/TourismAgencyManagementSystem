package view;

import business.FacilityManager;
import business.HotelManager;
import business.PensionTypeManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
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
    private JTable tbl_uns_pension_type;
    private JPanel unf_panel;
    private JButton btn_save_hotel;
    private JLabel lbl_winter;
    private JLabel lbl_summer;
    private JLabel lbl_star;
    private JLabel lbl_finish;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JFormattedTextField formattedTextField3;
    private JFormattedTextField formattedTextField4;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {};
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};
    DefaultTableModel unselectedPensionMdl = new DefaultTableModel();
    private String[] unselectedPensions = {};
    DefaultTableModel selectedPensionMdl = new DefaultTableModel();
    private String[] selectedPension = {};
    private final Hotel hotel;
    private final FacilityManager facilityManager;
    private final PensionTypeManager pensionTypeManager;
    private final HotelManager hotelManager;

    public HotelAddGUI() {
        this.hotel = new Hotel();
        this.facilityManager = new FacilityManager();
        this.hotelManager = new HotelManager();
        this.pensionTypeManager = new PensionTypeManager();
        this.add(container);
        this.guiInitilaze(500, 1000);
        container.setPreferredSize(new Dimension(500, 1000));

        // Hotel Management
        loadAddComponent();

        // Facilities Management
        loadLeftFacilityTable();
        loadRightFacilityTable();

        // Pension Types Management
        loadLeftPensionTable();
        loadRightPensionTable();

        reSizeComponent();

    }
    // Facilities
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

    // Pension Types
    public void loadLeftPensionTable(){
        Object[] col_pension_type_list = {"Pension Type Name"};
        ArrayList<Object[]> pensionTypeList = this.pensionTypeManager.getForTablePensions(col_pension_type_list.length);
        this.createTable(this.unselectedPensionMdl, this.tbl_pension_type, col_pension_type_list, pensionTypeList);
    }

    public void loadRightPensionTable(){
        selectedPensionMdl.addColumn("Pension Type Name");
        for (String pension : selectedPension){
            selectedPensionMdl.addRow(new Object[]{pension});
        }
        tbl_uns_pension_type.setModel(selectedPensionMdl);
    }

    // Add Table
    private void loadAddComponent() {
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                if (selectedRow != -1 && selectedRow < tbl_facilities.getRowCount()) {
                    tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });

        this.tbl_pension_type.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_pension_type.rowAtPoint(e.getPoint());
                if (selectedRow != -1 && selectedRow < tbl_pension_type.getRowCount()) {
                    tbl_pension_type.setRowSelectionInterval(selectedRow, selectedRow);
                }
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
                    || Helper.isTableEmpty(tbl_uns_facility)
                    || Helper.isTableEmpty(tbl_uns_pension_type)) {
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
                        selectedFacilities,
                        selectedPension
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

        // Facility Add and Remove
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

        // Pension Add and Remove
        btn_add_pension.addActionListener(e -> {
            int selectedRow = tbl_pension_type.getSelectedRow();
            if (selectedRow != -1){
                Object[] rowData = {tbl_pension_type.getValueAt(selectedRow,0)};
                selectedPensionMdl.addRow(rowData);
                String pensionTypeName = (String) rowData[0];
                selectedPension = addPensionToArray(selectedPension, pensionTypeName);
                unselectedPensionMdl.removeRow(selectedRow);
            }
        });
        btn_remove_pension.addActionListener(e -> {
            int selectedRow = tbl_uns_pension_type.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = {tbl_uns_pension_type.getValueAt(selectedRow, 0)};
                unselectedPensionMdl.addRow(rowData);
                String pensionTypeName = (String) rowData[0];
                unselectedPensions = addPensionToArray(unselectedPensions, pensionTypeName);
                selectedPensionMdl.removeRow(selectedRow);
                selectedPension = removePensionFromArray(selectedPension, pensionTypeName);
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

    private String[] addPensionToArray(String[] selectedPension, String pensionTypeName) {
        String[] newPensionType = new String[selectedPension.length + 1];
        System.arraycopy(selectedPension, 0, newPensionType, 0, selectedPension.length);
        newPensionType[newPensionType.length - 1] = pensionTypeName;
        return newPensionType;
    }

    private String[] removePensionFromArray(String[] selectedPension, String pensionTypeName) {
        String[] newPensionType = new String[selectedPension.length - 1];
        int index = 0;
        for (String pensionType : selectedPension) {
            if (!pensionType.equals(pensionTypeName)) {
                newPensionType[index++] = pensionType;
            }
        }
        return newPensionType;
    }

    public void reSizeComponent() {
        this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_facilities.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();

        this.tbl_uns_facility.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_uns_facility.revalidate();
        this.tbl_uns_facility.repaint();

        this.tbl_pension_type.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_pension_type.setPreferredSize(new Dimension(tbl_pension_type.getWidth(), 119));
        this.tbl_pension_type.revalidate();
        this.tbl_pension_type.repaint();

        this.tbl_uns_pension_type.setPreferredSize(new Dimension(tbl_uns_pension_type.getWidth(), 119));
        this.tbl_uns_pension_type.revalidate();
        this.tbl_uns_pension_type.repaint();

    }

    private void createUIComponents() throws ParseException {
        this.formattedTextField1 = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.formattedTextField2 = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.formattedTextField3 = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.formattedTextField4 = new JFormattedTextField(new MaskFormatter("##/##/####"));
    }
}
