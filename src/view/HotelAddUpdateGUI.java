package view;

import business.HotelManager;
import business.PensionTypeManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelAddUpdateGUI extends Layout {
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
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {
            "Ücretsiz Otopark",
            "Ücretsiz WiFi",
            "Yüzme Havuzu",
            "Fitness Center",
            "Hotel Concierge",
            "SPA",
            "7/24 Oda Servisi"
    };
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};

    private final PensionTypeManager pensionTypeManager;
    private final HotelManager hotelManager;

    public HotelAddUpdateGUI(Hotel hotel) {
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
        }
        // Hotel Management
        loadHotelComponent();

        // Facilities Management
        loadFacilityTable();
        loadHotelFacilityTable();


        // Pension Type Management
//        loadPensionTypeTable();
//        loadPensionTypeComponent();

        //reSizeComponent();

    }

    private void loadFacilityTable() {
        unselectedFacilitiesMdl.addColumn("Facility Name");
        for(String facility : unselectedFacilities){
            unselectedFacilitiesMdl.addRow(new Object[]{facility});
        }
        tbl_facilities.setModel(unselectedFacilitiesMdl);
    }

    private void loadHotelFacilityTable() {
        selectedFacilitiesMdl.addColumn("Facility Name");
        for(String facility : selectedFacilities){
            selectedFacilitiesMdl.addRow(new Object[]{facility});
        }
        tbl_uns_facility.setModel(selectedFacilitiesMdl);
    }


    private void loadHotelComponent() {
        this.btn_save_hotel.addActionListener(e -> {
            if (Helper.isFieldEmpty(this.txtf_hotel_name)
                    || Helper.isFieldEmpty(this.txtf_hotel_city)
                    || Helper.isFieldEmpty(this.txtf_hotel_region)
                    || Helper.isFieldEmpty(this.txtf_hotel_full_address)
                    || Helper.isFieldEmpty(this.txtf_hotel_email)
                    || Helper.isFieldEmpty(this.txtf_hotel_phone)
                    || Helper.isFieldEmpty(this.txtf_hotel_star)) {
                Helper.showMsg("fill");
            } else {
                boolean result = true;
                if (this.hotel == null) {
                    Hotel obj = new Hotel(
                            txtf_hotel_name.getText(),
                            txtf_hotel_city.getText(),
                            txtf_hotel_region.getText(),
                            txtf_hotel_full_address.getText(),
                            txtf_hotel_email.getText(),
                            txtf_hotel_phone.getText(),
                            txtf_hotel_star.getText(),
                            selectedFacilities);
                    result = this.hotelManager.save(obj);
                } else {
                    this.hotel.setName(txtf_hotel_name.getText());
                    this.hotel.setCity(txtf_hotel_city.getText());
                    this.hotel.setRegion(txtf_hotel_region.getText());
                    this.hotel.setFullAddress(txtf_hotel_full_address.getText());
                    this.hotel.setEmail(txtf_hotel_email.getText());
                    this.hotel.setPhone(txtf_hotel_phone.getText());
                    this.hotel.setStar(txtf_hotel_star.getText());
                    this.hotelManager.update(this.hotel);
                }
                if (result) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
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

    // hotels
    // hotelName
    //newHotels
    //hotel


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
