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
import java.util.ArrayList;

public class HotelUpdateGUI extends Layout{
    private JPanel hotel_lbls;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_reion;
    private JLabel lbl_hotel_full_address;
    private JLabel lbl_hotel_email;
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_star;
    private JPanel hotel_txtfs;
    private JTextField txtf_hotel_name;
    private JTextField txtf_hotel_city;
    private JTextField txtf_hotel_region;
    private JTextField txtf_hotel_full_address;
    private JTextField txtf_hotel_email;
    private JTextField txtf_hotel_phone;
    private JTextField txtf_hotel_star;
    private JPanel unf_panel;
    private JTabbedPane tabbedPane_unFclty;
    private JTable tbl_facilities;
    private JTabbedPane tabbedPane2;
    private JTable tbl_uns_facility;
    private JButton btn_add_facility;
    private JButton btn_remove_facility;
    private JTabbedPane tabbedPane3;
    private JTable tbl_pension_type;
    private JTabbedPane tabbedPane4;
    private JTable table4;
    private JButton btn_add_pension;
    private JButton btn_remove_pension;
    private JButton btn_save_hotel;
    private JLabel lbl_hotel_add_update;
    private JPanel container;
    private final Hotel hotel;
    private FacilityManager facilityManager;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {
    };
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};

    private final PensionTypeManager pensionTypeManager;
    private final HotelManager hotelManager;


    public HotelUpdateGUI(Hotel hotel) {
        this.facilityManager = new FacilityManager();
        this.hotelManager = new HotelManager();
        this.pensionTypeManager = new PensionTypeManager();
        this.add(container);
        this.guiInitilaze(500, 720);
        container.setPreferredSize(new Dimension(1000, 720));
        this.hotel = hotel;
        if (this.hotel != null) {
            this.txtf_hotel_name.setText(hotel.getName());
            this.txtf_hotel_city.setText(hotel.getCity());
            this.txtf_hotel_region.setText(hotel.getRegion());
            this.txtf_hotel_full_address.setText(hotel.getFullAddress());
            this.txtf_hotel_email.setText(hotel.getEmail());
            this.txtf_hotel_phone.setText(hotel.getPhone());
            this.txtf_hotel_star.setText(hotel.getStar());
            String[] facilities = hotel.getFacilities();
            if (facilities != null && facilities.length > 0) {
                StringBuilder tooltipText = new StringBuilder();
                for (String facility : facilities) {
                    tooltipText.append(facility).append("\n");
                }
                tbl_uns_facility.setToolTipText(tooltipText.toString());
            }

        }
        // Hotel Management
        loadHotelComponent();

        // Facilities Management
        loadLeftTable();
        loadRightTable();


        reSizeComponent();

    }

    private void loadLeftTable() {
        Object[] col_facility_list = {"Facility Name"};
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableFacilities(col_facility_list.length);
        this.createTable(this.unselectedFacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
    }
    private void loadRightTable() {
        Object[] col_facility_list = {"Facility Name"};
        int hotelId = hotel.getId(); // Otel ID'sini al
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableHotelFacility(col_facility_list.length, hotelId);
        this.createTable(this.selectedFacilitiesMdl, this.tbl_uns_facility, col_facility_list, facilityList);
    }


    private void loadHotelComponent() {
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.tbl_uns_facility.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_uns_facility.rowAtPoint(e.getPoint());
                tbl_uns_facility.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.btn_save_hotel.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.txtf_hotel_name)
                    || Helper.isFieldEmpty(this.txtf_hotel_city)
                    || Helper.isFieldEmpty(this.txtf_hotel_region)
                    || Helper.isFieldEmpty(this.txtf_hotel_full_address)
                    || Helper.isFieldEmpty(this.txtf_hotel_email)
                    || Helper.isFieldEmpty(this.txtf_hotel_phone)
                    || Helper.isFieldEmpty(this.txtf_hotel_star)
                    || Helper.isTableEmpty(this.tbl_uns_facility)) {
                Helper.showMsg("fill");
            } else {
                String[] selectedFacilities = getSelectedFacilities();

                    this.hotel.setName(txtf_hotel_name.getText());
                    this.hotel.setCity(txtf_hotel_city.getText());
                    this.hotel.setRegion(txtf_hotel_region.getText());
                    this.hotel.setFullAddress(txtf_hotel_full_address.getText());
                    this.hotel.setEmail(txtf_hotel_email.getText());
                    this.hotel.setPhone(txtf_hotel_phone.getText());
                    this.hotel.setStar(txtf_hotel_star.getText());

                this.hotel.setFacilities(selectedFacilities);
                this.hotelManager.update(this.hotel);
                }
                if (hotelManager.update(this.hotel)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
        });

        btn_add_facility.addActionListener(e -> {
            int selectedRow = tbl_facilities.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = {tbl_facilities.getValueAt(selectedRow, 0)};
                String facilityName = (String) rowData[0];
                if (isFacilityAlreadySelected(facilityName)) {
                    JOptionPane.showMessageDialog(container, "Bu özellik zaten seçili.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                } else {
                    selectedFacilitiesMdl.addRow(rowData);
                    selectedFacilities = addFacilityToArray(selectedFacilities, facilityName);
                    unselectedFacilitiesMdl.removeRow(selectedRow);
                }
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
                selectedFacilities = addFacilityToArray(selectedFacilities, facilityName);
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

        this.tbl_uns_facility.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_uns_facility.revalidate();
        this.tbl_uns_facility.repaint();

        this.tbl_pension_type.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_pension_type.revalidate();
        this.tbl_pension_type.repaint();
    }

    private String[] getSelectedFacilities() {
        DefaultTableModel model = (DefaultTableModel) tbl_uns_facility.getModel();
        ArrayList<String> selectedFacilitiesList = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            selectedFacilitiesList.add((String) model.getValueAt(i, 0));
        }
        return selectedFacilitiesList.toArray(new String[0]);
    }

    private boolean isFacilityAlreadySelected(String facilityName) {
        for (int i = 0; i < selectedFacilitiesMdl.getRowCount(); i++) {
            if (facilityName.equals(selectedFacilitiesMdl.getValueAt(i, 0))) {
                return true;
            }
        }
        return false;
    }
}
