package view;

import business.*;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.util.ArrayList;

// Represents the graphical user interface for adding a room
public class ADDRoomGUI extends Layout {
    private JComboBox<ComboItem> cmb_room_add_hotel;
    private JComboBox<ComboItem> cmb_season_add;
    private JComboBox<ComboItem> cmb_room_type_add;
    private JTextField tf_adult_price;
    private JRadioButton rbut_projection;
    private JButton btn_save_add_room;
    private JPanel container;
    private JComboBox<ComboItem> cmb_pension_add;
    private JTextField tf_child_price;
    private JTextField tf_bed_capacity;
    private JTextField tf_square_meter;
    private JRadioButton rbut_television;
    private JRadioButton rbut_minibar;
    private JRadioButton rbut_game_console;
    private JRadioButton rbut_cashbox;
    private JTextField tf_stock;
    private JLabel lbl_room;
    private HotelManager hotelManager;
    private SeasonManager seasonManager;
    private PensionManager pensionManager;
    private Hotel hotel;
    private Room room;
    private Season season;
    private RoomManager roomManager;

    // Constructor for ADDRoomGUI
    public ADDRoomGUI() {
        this.add(container);
        this.guiInitialize(725, 550);
        this.hotel = new Hotel();
        this.room = new Room();
        this.season = new Season();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();

        // Populate hotel combo box
        for (Hotel hotel : hotelManager.findAll()) {
            this.cmb_room_add_hotel.addItem(hotel.getComboItem());
        }

        // Handle hotel combo box selection change event
        cmb_room_add_hotel.addActionListener(e -> {
            ComboItem selectedHotelItem = (ComboItem) cmb_room_add_hotel.getSelectedItem();

            // Populate pension combo box based on selected hotel
            ArrayList<Pension> pensions = pensionManager.getPensionByOtelId(selectedHotelItem.getKey());
            cmb_pension_add.removeAllItems();

            for (Pension pension : pensions) {
                cmb_pension_add.addItem(pension.getComboItem());
            }

            // Populate season combo box based on selected hotel
            ArrayList<Season> seasons = seasonManager.getSeasonsByOtelId(selectedHotelItem.getKey());
            cmb_season_add.removeAllItems();

            for (Season season : seasons) {
                cmb_season_add.addItem(season.getComboItem());
            }
        });

        // Handle save button click event
        btn_save_add_room.addActionListener(e -> {
            JTextField[] selectedRoomList = {tf_adult_price, tf_child_price, tf_bed_capacity, tf_bed_capacity, tf_square_meter};
            if (Helper.isComboBoxEmpty(cmb_room_add_hotel)
                    || Helper.isComboBoxEmpty(cmb_pension_add)
                    || Helper.isComboBoxEmpty(cmb_season_add)
                    || Helper.isComboBoxEmpty(cmb_room_type_add)
                    || Helper.isFieldListEmpty(selectedRoomList)) {
                Helper.showMsg("Please fill in all fields.");
            } else {
                ComboItem selectedHotel = (ComboItem) cmb_room_add_hotel.getSelectedItem();
                ComboItem selectedPension = (ComboItem) cmb_pension_add.getSelectedItem();
                ComboItem selectedSeason = (ComboItem) cmb_season_add.getSelectedItem();

                ComboItem selectedHotelId = (ComboItem) cmb_room_add_hotel.getSelectedItem();
                int hotelId = selectedHotelId.getKey();

                // Set room properties
                room.setHotel_name(selectedHotel.getValue());
                room.setHotel_city(roomManager.getHotelCity(hotelId));
                room.setType((String) cmb_room_type_add.getSelectedItem());
                room.setStock(Integer.parseInt(tf_stock.getText()));
                room.setAdult_price(Double.parseDouble(tf_adult_price.getText()));
                room.setChild_price(Double.parseDouble(tf_child_price.getText()));
                room.setBed_capacity(Integer.parseInt(tf_bed_capacity.getText()));
                room.setSquare_meter(Integer.parseInt(tf_square_meter.getText()));
                room.setTelevision(rbut_television.isSelected());
                room.setMinibar(rbut_minibar.isSelected());
                room.setGame_console(rbut_game_console.isSelected());
                room.setCash_box(rbut_cashbox.isSelected());
                room.setProjection(rbut_projection.isSelected());
                room.setHotel_id(selectedHotel.getKey());
                room.setPension_id(selectedPension.getKey());
                room.setSeason_id(selectedSeason.getKey());

                // Save room
                boolean result = false;
                if (room.getRoom_id() == 0) {
                    result = roomManager.save(room);
                }
                if (result) {
                    Helper.showMsg("Room added successfully.");
                    dispose();
                } else {
                    Helper.showMsg("Error occurred while adding room.");
                }
            }
        });
    }
}
