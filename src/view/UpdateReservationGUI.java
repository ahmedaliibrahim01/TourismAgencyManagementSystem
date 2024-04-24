package view;

import business.FacilityManager;
import business.ReservationManager;
import business.RoomManager;
import business.SeasonManager;
import core.Helper;
import entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * UpdateReservationGUI provides an interface for updating a reservation.
 * This class presents an interface using Swing components to update reservation details.
 */
public class UpdateReservationGUI extends Layout {
    private JPanel container;
    private JTextField tf_res_hotel_name;
    private JTextField tf_res_city;
    private JTextField tf_res_star;
    private JTextField tf_res_roomtype;
    private JTextField tf_res_room_field;
    private JTextField tf_res_bed_capacity;
    private JTextField tf_res_start_date;
    private JTextField tf_res_end_date1;
    private JRadioButton rbut_television;
    private JRadioButton rbut_game_console;
    private JRadioButton rbut_cash_vault;
    private JRadioButton rbut_res_projection;
    private JRadioButton rbut_res_minibar;
    private JTextField tf_res_pension;
    private JTable tbl_facilities1;
    private JButton btn;
    private JFormattedTextField star_date;
    private JFormattedTextField end_date;
    private JTextField txtf_region;
    private JTextField txtf_address;
    private JTextField txtf_phne;
    private JTabbedPane tabbedPane1;
    private JTextField txtf_season;
    private JTabbedPane tabbedPane3;
    private JButton btn_calculate;
    private JTextField txtf_total_amount;
    private JTextField txtf_guest_name;
    private JTextField txtf_guest_phone;
    private JTextField txtf_guest_email;
    private JTextField txtf_guest_id;
    private JTextField txtf_guest_note;
    private JButton btn_save;
    private JComboBox cmbx_adult;
    private JComboBox cmbx_children;
    private JTextField txtf_adult_price;
    private JTextField txtf_children_price;
    private ReservationManager reservationManager = new ReservationManager();
    private Season season;
    private Pension pension;
    private SeasonManager seasonManager;
    private RoomManager roomManager;
    private String check_in_date;
    private String check_out_date;
    private Double adult_price;
    private Double child_price;
    private double totalAmount;
    private FacilityManager facilityManager;
    private Hotel hotel;
    DefaultTableModel mdl_FacilitiesMdl1 = new DefaultTableModel();
    private Room room;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Reservation reservation;

    /**
     * Constructor for UpdateReservationGUI class.
     * Initializes necessary objects and creates the interface for updating a reservation.
     * @param room The room for which the reservation is being updated
     * @param hotel The hotel associated with the reservation
     * @param pension The pension associated with the reservation
     * @param season The season associated with the reservation
     * @param reservation The reservation being updated
     */
    public UpdateReservationGUI(Room room, Hotel hotel, Pension pension, Season season, Reservation reservation) {
        this.add(container);
        this.guiInitialize(1000, 700);
        this.facilityManager = new FacilityManager();
        this.hotel = hotel;
        this.pension = pension;
        this.season = season;
        this.room = room;
        this.reservation = reservation;
        this.roomManager = new RoomManager();

        loadReservationComponent();

        // Populate fields with reservation details
        this.tf_res_hotel_name.setText(room.getHotel_name());
        this.tf_res_city.setText(room.getHotel_city());
        this.txtf_region.setText(hotel.getRegion());
        this.txtf_address.setText(hotel.getFullAddress());
        this.txtf_phne.setText(hotel.getPhone());
        this.tf_res_star.setText(hotel.getStar());

        facilityTable();

        this.tf_res_roomtype.setText(room.getType());
        this.tf_res_pension.setText(pension.getPension_type());
        this.tf_res_bed_capacity.setText(String.valueOf(room.getBed_capacity()));
        this.tf_res_room_field.setText(String.valueOf(room.getSquare_meter()));
        this.txtf_season.setText(String.valueOf(season.getStart_date()) + " to " + String.valueOf(season.getFinish_date()));
        this.txtf_adult_price.setText(String.valueOf(this.room.getAdult_price()));
        this.txtf_children_price.setText(String.valueOf(this.room.getChild_price()));
        this.rbut_television.setSelected(room.isTelevision());
        this.rbut_game_console.setSelected(room.isGame_console());
        this.rbut_cash_vault.setSelected(room.isCash_box());
        this.rbut_res_projection.setSelected(room.isProjection());
        this.rbut_res_minibar.setSelected(room.isMinibar());

        // Populate adult count dropdown
        int adult = room.getBed_capacity();
        for (int i = 0; i <= adult; i++){
            this.cmbx_adult.addItem(i);
        }

        // Populate children count dropdown based on room type
        String type = room.getType();
        int child;
        if (type.equals("Single Room")){
            child = 1;
            for (int i = 0; i <= child; i++){
                this.cmbx_children.addItem(i);
            }
        }else if (type.equals("Double Room")){
            child = 2;
            for (int i = 0; i <= child; i++){
                this.cmbx_children.addItem(i);
            }
        }else if (type.equals("Junior Suite Room")) {
            child = 3;
            for (int i = 0; i <= child; i++) {
                this.cmbx_children.addItem(i);
            }
        }else if (type.equals("Suite Room")) {
            child = 5;
            for (int i = 0; i <= child; i++) {
                this.cmbx_children.addItem(i);
            }
        }

        // Calculate total amount based on selected dates and guest counts
        LocalDate checkInDate = LocalDate.parse(star_date.getText(), this.formatter);
        LocalDate checkOutDate = LocalDate.parse(end_date.getText(), formatter);
        long dayCount = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        double adultPrice = this.room.getAdult_price();
        double childrenPrice = this.room.getChild_price();
        int adultCount = Integer.parseInt(this.cmbx_adult.getSelectedItem().toString());
        int childCount = Integer.parseInt(cmbx_children.getSelectedItem().toString());

        totalAmount = ((adultPrice * adultCount) + (childrenPrice * childCount)) * dayCount;

        this.txtf_total_amount.setText(String.valueOf(totalAmount));
    }

    /**
     * Loads components for updating a reservation.
     */
    private void loadReservationComponent() {
        btn_save.addActionListener(e -> {
            JTextField[] checkField = {this.txtf_guest_name, this.txtf_guest_phone, this.txtf_guest_email, this.txtf_guest_id};
            if (Helper.isFieldListEmpty(checkField)) {
                Helper.showMsg("Lütfen tüm alanları doldurun.");
            } else {
                // Set reservation details
                this.reservation.setRoom_id(this.room.getRoom_id());
                this.txtf_guest_name.setText(reservation.getGuest_name());
                this.reservation.setGuest_name(this.txtf_guest_name.getText());
                this.reservation.setGuest_id(this.txtf_guest_id.getText());
                this.reservation.setGuest_email(this.txtf_guest_email.getText());
                this.reservation.setGuest_phone(this.txtf_guest_phone.getText());
                this.reservation.setAdult_count(this.cmbx_adult.getItemCount());
                this.reservation.setChild_count(this.cmbx_children.getItemCount());
                this.reservation.setCheck_in_date(LocalDate.parse(this.star_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                this.reservation.setCheck_out_date(LocalDate.parse(this.end_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                this.reservation.setGuest_note(this.txtf_guest_note.getText());
                this.reservation.setTotal_price(Double.parseDouble(this.txtf_total_amount.getText()));
                this.reservation.setHotel_id(this.hotel.getId());

                // Save the reservation
                boolean result = this.reservationManager.save(this.reservation);
                if (result) {
                    Helper.showMsg("Rezervasyon başarıyla kaydedildi.");
                    dispose();
                } else {
                    Helper.showMsg("Rezervasyon kaydedilirken bir hata oluştu.");
                }
            }
        });

        btn_calculate.addActionListener(e -> {
            LocalDate checkInDate = LocalDate.parse(star_date.getText(), formatter);
            LocalDate checkOutDate = LocalDate.parse(end_date.getText(), formatter);
            long dayCount = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            double adultPrice = this.room.getAdult_price();
            double childrenPrice = this.room.getChild_price();
            int adultCount = Integer.parseInt(this.cmbx_adult.getSelectedItem().toString());
            int childCount = Integer.parseInt(cmbx_children.getSelectedItem().toString());

            totalAmount = ((adultPrice * adultCount) + (childrenPrice * childCount)) * dayCount;

            this.txtf_total_amount.setText(String.valueOf(totalAmount));
        });
    }

    /**
     * Creates the UI components.
     * @throws ParseException If there's an error parsing the date format
     */
    private void createUIComponents() throws ParseException{
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tomorrow = today.plusDays(1);

        // Set default start and end dates
        this.star_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.star_date.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.end_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        end_date.setText(tomorrow.format(formatter));
    }

    // Facility Table
    private void facilityTable(){
        Object[] col_facility_list = {"Facility Name"};
        int selectedHotelId = hotel.getId();
        if (selectedHotelId != -1) {
            ArrayList<Object[]> facilityList = this.facilityManager.getForTableHotelFacility(col_facility_list.length, selectedHotelId);
            this.createTable(this.mdl_FacilitiesMdl1, this.tbl_facilities1, col_facility_list, facilityList);
            this.tbl_facilities1.getColumnModel().getColumn(0).setMaxWidth(200);
        } else {
            this.mdl_FacilitiesMdl1.setRowCount(0);
        }
    }
}
