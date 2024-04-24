package view;

import business.HotelManager;
import business.PensionManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Pension;
import entity.User;

import javax.swing.*;

/**
 * PensionGUI class provides a graphical interface for managing pension information.
 * This class allows users to save pension data associated with a hotel.
 */
public class PensionGUI extends Layout {
    private JPanel wrap;
    private JButton btn_save_pension;
    private JComboBox cmb_persion;
    private JLabel lbl_pan_add_menu;
    private JLabel lbl_hotel_id;
    private JComboBox cmb_hotel;
    private JTextField tf_hotel_name;
    private PensionManager pensionManager;
    private Pension pension;
    private HotelManager hotelManager;
    private Hotel hotel;
    private User user;

    /**
     * Constructs a PensionGUI object.
     * Initializes necessary components and sets up the GUI for managing pension data.
     * @param hotel The hotel associated with the pension data
     */
    public PensionGUI(Hotel hotel) {
        // Initialize managers and entities
        this.hotelManager = new HotelManager();
        this.hotel = hotel;
        this.pensionManager = new PensionManager();
        this.pension = new Pension();

        // Add components to the layout
        this.cmb_persion.getSelectedItem();
        this.add(wrap);
        this.guiInitialize(375, 250);

        // Populate hotel combo box if hotel is not null
        if (this.hotel != null) {
            this.cmb_hotel.addItem(hotel.getComboItem());
        } else {
            dispose();
        }

        // Define action for save button
        this.btn_save_pension.addActionListener(e -> {
            boolean result = false;
            ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();
            this.pension.setHotel_id(selectedHotel.getKey());
            this.pension.setPension_type(cmb_persion.getSelectedItem().toString());

            // Save pension data if hotel ID is not zero
            if (this.pension.getHotel_id() != 0) {
                result = this.pensionManager.save(this.pension);
            }

            // Show result message and dispose the window
            if (result) {
                Helper.showMsg("done");
                dispose();
            } else {
                Helper.showMsg("error");
            }
        });
    }
}