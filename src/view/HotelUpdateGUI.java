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
 * HotelUpdateGUI class provides an interface for updating hotel information.
 * This class presents an interface using Swing components to modify hotel details and facilities.
 */
public class HotelUpdateGUI extends Layout {
    // UI components
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_region;
    private JLabel lbl_hotel_full_address;
    private JLabel lbl_hotel_email;
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_star;
    private JPanel hotel_txtfs1;
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
    private JButton btn_save_hotel;
    private JLabel lbl_hotel_add_update;
    private JPanel container;
    private JPanel hotel_lbls;
    private JPanel hotel_lbls2;
    private FacilityManager facilityManager;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {};
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};
    private final Hotel hotel;
    private final HotelManager hotelManager;

    /**
     * Constructor for HotelUpdateGUI class.
     * Initializes necessary objects and creates the interface for updating a hotel.
     * @param hotel The hotel object to be updated.
     */
    public HotelUpdateGUI(Hotel hotel) {
        // Initialize objects
        this.facilityManager = new FacilityManager();
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitialize(500, 530);
        container.setPreferredSize(new Dimension(500, 530));
        this.hotel = hotel;

        // Populate fields with existing hotel information
        if (this.hotel != null) {
            this.txtf_hotel_name.setText(hotel.getName());
            this.txtf_hotel_city.setText(hotel.getCity());
            this.txtf_hotel_region.setText(hotel.getRegion());
            this.txtf_hotel_full_address.setText(hotel.getFullAddress());
            this.txtf_hotel_email.setText(hotel.getEmail());
            this.txtf_hotel_phone.setText(hotel.getPhone());
            this.txtf_hotel_star.setText(hotel.getStar());

            // Set tooltip for facilities table with existing facility names
            String[] facilities = hotel.getFacilities();
            if (facilities != null && facilities.length > 0) {
                StringBuilder tooltipText = new StringBuilder();
                for (String facility : facilities) {
                    tooltipText.append(facility).append("\n");
                }
                tbl_uns_facility.setToolTipText(tooltipText.toString());
            }
        }
        // Load UI components and data
        loadHotelComponent();
        loadLeftFacilityTable();
        loadRightFacilityTable();
        reSizeComponent();
    }

    // Facilities Table
    private void loadLeftFacilityTable() {
        // Load available facilities into the left table
        Object[] col_facility_list = {"Facility Name"};
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableFacilities(col_facility_list.length);
        this.createTable(this.unselectedFacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
    }

    private void loadRightFacilityTable() {
        // Load selected facilities of the hotel into the right table
        Object[] col_facility_list = {"Facility Name"};
        int hotelId = hotel.getId(); // Get hotel ID
        ArrayList<Object[]> facilityList = this.facilityManager.getForTableHotelFacility(col_facility_list.length, hotelId);
        this.createTable(this.selectedFacilitiesMdl, this.tbl_uns_facility, col_facility_list, facilityList);
    }

    private void loadHotelComponent() {
        // Add mouse listener to facilities table
        this.tbl_facilities.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_facilities.rowAtPoint(e.getPoint());
                tbl_facilities.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        // Add mouse listener to selected facilities table
        this.tbl_uns_facility.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_uns_facility.rowAtPoint(e.getPoint());
                tbl_uns_facility.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        // Add action listener to save hotel button
        this.btn_save_hotel.addActionListener(e -> {
            if (Helper.isFieldEmpty(txtf_hotel_name)
                    || Helper.isFieldEmpty(txtf_hotel_city)
                    || Helper.isFieldEmpty(txtf_hotel_region)
                    || Helper.isFieldEmpty(txtf_hotel_full_address)
                    || Helper.isFieldEmpty(txtf_hotel_email)
                    || Helper.isFieldEmpty(txtf_hotel_phone)
                    || Helper.isFieldEmpty(txtf_hotel_star)
                    || Helper.isTableEmpty(tbl_uns_facility)) {
                Helper.showMsg("fill");
            } else {
                String[] selectedFacilities = getSelectedFacilities();

                // Update hotel information with new values
                hotel.setName(txtf_hotel_name.getText());
                hotel.setCity(txtf_hotel_city.getText());
                hotel.setRegion(txtf_hotel_region.getText());
                hotel.setFullAddress(txtf_hotel_full_address.getText());
                hotel.setEmail(txtf_hotel_email.getText());
                hotel.setPhone(txtf_hotel_phone.getText());
                hotel.setStar(txtf_hotel_star.getText());
                hotel.setFacilities(selectedFacilities);

                // Update the hotel in the database
                if (hotelManager.update(hotel)) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        // Add action listener to add facility button
        btn_add_facility.addActionListener(e -> {
            int selectedRow = tbl_facilities.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = {tbl_facilities.getValueAt(selectedRow, 0)};
                String facilityName = (String) rowData[0];
                if (isFacilityAlreadySelected(facilityName)) {
                    JOptionPane.showMessageDialog(container, "This facility is already selected.", "Notice", JOptionPane.WARNING_MESSAGE);
                } else {
                    selectedFacilitiesMdl.addRow(rowData);
                    selectedFacilities = addFacilityToArray(selectedFacilities, facilityName);
                    unselectedFacilitiesMdl.removeRow(selectedRow);
                }
            }
        });

        // Add action listener to remove facility button
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

    /**
     * Add a facility name to the selected facilities array.
     * @param selectedFacilities Array of selected facilities
     * @param facilityName Name of the facility to add
     * @return Updated array of selected facilities
     */
    private String[] addFacilityToArray(String[] selectedFacilities, String facilityName) {
        String[] newFacilities = new String[selectedFacilities.length + 1];
        System.arraycopy(selectedFacilities, 0, newFacilities, 0, selectedFacilities.length);
        newFacilities[newFacilities.length - 1] = facilityName;
        return newFacilities;
    }

    /**
     * Get the selected facilities from the table.
     * @return Array of selected facilities
     */
    private String[] getSelectedFacilities() {
        DefaultTableModel model = (DefaultTableModel) tbl_uns_facility.getModel();
        ArrayList<String> selectedFacilitiesList = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            selectedFacilitiesList.add((String) model.getValueAt(i, 0));
        }
        return selectedFacilitiesList.toArray(new String[0]);
    }

    /**
     * Check if a facility is already selected.
     * @param facilityName Name of the facility to check
     * @return True if facility is already selected, otherwise false
     */
    private boolean isFacilityAlreadySelected(String facilityName) {
        for (int i = 0; i < selectedFacilitiesMdl.getRowCount(); i++) {
            if (facilityName.equals(selectedFacilitiesMdl.getValueAt(i, 0))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resize components to fit properly within the UI.
     */
    public void reSizeComponent() {
        // Resize facilities tables
        this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_facilities.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_facilities.revalidate();
        this.tbl_facilities.repaint();

        this.tbl_uns_facility.getColumnModel().getColumn(0).setMaxWidth(200);
        this.tbl_uns_facility.setPreferredSize(new Dimension(tbl_facilities.getWidth(), 119));
        this.tbl_uns_facility.revalidate();
        this.tbl_uns_facility.repaint();
    }
}
