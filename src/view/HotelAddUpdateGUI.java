package view;

import business.HotelManager;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class HotelAddUpdateGUI extends Layout{
    private JPanel container;
    private JTextField txtf_hotel_name;
    private JTextField txtf_hotel_city;
    private JTextField txtf_hotel_region;
    private JTextField txtf_hotel_full_address;
    private JTextField txtf_hotel_email;
    private JTextField txtf_hotel_phone;
    private JTextField txtf_hotel_star;
    private JButton addButton;
    private JButton removeButton;
    private JButton addButton1;
    private JButton removeButton1;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JTable table4;
    private JLabel lbl_hotel_add_update;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_reion;
    private JLabel lbl_hotel_full_address;
    private JLabel lbl_hotel_email;
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_star;
    private User user;
    private HotelManager hotelManager;

    public HotelAddUpdateGUI(User user) {
        this.hotelManager = new HotelManager();
        this.add(container);
        this.guiInitilaze(500, 700);
        container.setPreferredSize(new Dimension(1000,700));
        this.user = user;
        if (this.user == null) {
            dispose();
        }
    }
}
