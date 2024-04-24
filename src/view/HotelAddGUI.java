package view;

import business.FacilityManager;
import business.HotelManager;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * HotelAddGUI provides an interface for adding a hotel.
 * This class presents an interface using Swing components to input and save hotel information.
 */
public class HotelAddGUI extends Layout {
    // UI components
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
    private JTable tbl_facilities;
    private JPanel hotel_lbls;
    private JPanel hotel_txtfs;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_region;
    private JLabel lbl_hotel_full_address;
    private JLabel lbl_hotel_email;
    private JLabel lbl_hotel_add_update; // Typo: should be "address" instead of "add_update"
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_star;
    private JTabbedPane tabbedPane_unFacilities;
    private JTabbedPane tabbedPane_selected_Facilities;
    private JTable tbl_uns_facility;
    private JPanel unf_panel;
    private JButton btn_save_hotel;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {};
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};
    private final Hotel hotel;
    private final FacilityManager facilityManager;
    private final HotelManager hotelManager;

    /**
     * Constructor for HotelAddGUI class.
     * Initializes necessary objects and creates the interface for adding a new hotel.
     */
    public HotelAddGUI() {
        // Initialize objects
        this.hotel = new Hotel();
        this.facilityManager = new FacilityManager();
        this.hotelManager = new HotelManager();

        // Add container to layout and set initial size
        this.add(container);
        this.guiInitialize(500, 530); // Typo: should be "initialize" instead of "initilaze"
        container.setPreferredSize(new Dimension(500, 530));

        // Facilities Management
        loadLeftFacilityTable();
        loadRightFacilityTable();
        loadAddHotelComponent();
        reSizeComponent();
    }

    // Facilities
    /**
     * Loads the left facility table.
     * Lists all facilities for user selection.
     */
    private void loadLeftFacilityTable() {
        Object[] col_facility_list = {"Facility Name"};
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableFacilities(col_facility_list.length);
        this.createTable(this.unselectedFacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
    }

    /**
     * Loads the right facility table.
     * Displays selected facilities for user review.
     */
    private void loadRightFacilityTable() {
        selectedFacilitiesMdl.addColumn("Facility Name");
        for(String facility : selectedFacilities){
            selectedFacilitiesMdl.addRow(new Object[]{facility});
        }
        tbl_uns_facility.setModel(selectedFacilitiesMdl);
    }

    // Add Table

    /**
     * Loads the components for adding a hotel.
     * Defines necessary button listeners.
     */
    private void loadAddHotelComponent() {
        // Add mouse listener to facilities table
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                if (selectedRow != -1 && selectedRow < tbl_facilities.getRowCount()) {
                    tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        });

        // Save hotel button action listener
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
        });

        // Facility Add and Remove button action listeners
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

    /**
     * Adds a facility to the array of selected facilities.
     * @param selectedFacilities current array of selected facilities
     * @param facilityName facility to be added
     * @return reference to the new array
     */
    private String[] addFacilityToArray(String[] selectedFacilities, String facilityName) {
        String[] newFacilities = new String[selectedFacilities.length + 1];
        System.arraycopy(selectedFacilities, 0, newFacilities, 0, selectedFacilities.length);
        newFacilities[newFacilities.length - 1] = facilityName;
        return newFacilities;
    }

    /**
     * Removes a facility from the array of selected facilities.
     * @param selectedFacilities current array of selected facilities
     * @param facilityName facility to be removed
     * @return reference to the new array
     */
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

    /**
     * Resizes components for proper display.
     */
    public void reSizeComponent() {
        this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_facilities.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();

        this.tbl_uns_facility.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_uns_facility.revalidate();
        this.tbl_uns_facility.repaint();
    }
}
