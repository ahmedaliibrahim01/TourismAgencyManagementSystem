package view;

import business.HotelManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * SeasonGUI provides an interface for managing hotel seasons.
 * This class presents an interface using Swing components to add a new season for a hotel.
 */
public class SeasonGUI extends Layout {
    private JPanel wrap;
    private JButton btn_save_season;
    private JComboBox cmb_hotel_season;
    private JPanel wrap_season;
    private JLabel lbl_hotel_id;
    private JFormattedTextField tf_season_start;
    private JFormattedTextField tf_season_finish;
    private HotelManager hotelManager;
    private Hotel hotel;
    private SeasonManager seasonManager;
    private Season season;

    /**
     * Constructor for SeasonGUI class.
     * Initializes necessary objects and creates the interface for adding a new season.
     * @param hotel The hotel for which the season is being added
     */
    public SeasonGUI(Hotel hotel) {
        this.hotelManager = new HotelManager();
        this.hotel = hotel;
        this.seasonManager = new SeasonManager();
        this.season = new Season();
        this.cmb_hotel_season.getSelectedItem();
        this.add(wrap);
        this.guiInitialize(375, 300);

        // Populate hotel combo box with the selected hotel
        if (this.hotel != null){
            this.cmb_hotel_season.addItem(hotel.getComboItem());
        } else {
            dispose(); // Close the window if no hotel is selected
        }

        // Action listener for save button
        btn_save_season.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = false;
                ComboItem selectSeason = (ComboItem) cmb_hotel_season.getSelectedItem();
                season.setHotel_id(selectSeason.getKey());
                season.setSeason_type(cmb_hotel_season.getSelectedItem().toString());
                JFormattedTextField[] checkDateList = {tf_season_start, tf_season_finish};
                if (Helper.isFieldListEmpty(checkDateList)) {
                    Helper.showMsg("fill");
                } else {
                    try {
                        season.setStart_date(LocalDate.parse(tf_season_start.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        season.setFinish_date(LocalDate.parse(tf_season_finish.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        result = seasonManager.save(season);

                    } catch (DateTimeException ex) {
                        Helper.showMsg("Date Format is Wrong !");
                        return;
                    }
                }
                if (result) {
                    Helper.showMsg("done");
                    dispose(); // Close the window after saving
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    /**
     * Method to create the UI components.
     * @throws ParseException If there's an error parsing the date format
     */
    private void createUIComponents() throws ParseException {
        this.tf_season_start = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.tf_season_start.setText("01/06/2024");
        this.tf_season_finish = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.tf_season_finish.setText("01/12/2024");
    }
}
