package view;

import business.*;
import core.Helper;
import entity.Hotel;
import entity.Room;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Represents the graphical user interface for employee management
public class EmployeeGUI extends Layout {
    private JPanel container;
    private JTabbedPane tabbedPane_hotel;
    private JButton btn_employee_logout;
    private JTable tbl_hotels;
    private JLabel lbl_employee_panel;
    private JLabel lbl_employee_welcome;
    private JPanel pnl_hotel;
    private JPanel pnl_hotel_table;
    private JScrollPane scrl_hotel;
    private JButton btn_hotel_update;
    private JLabel lbl_hotel_update;
    private JButton btn_hotel_delete;
    private JLabel lbl_hotel_delete;
    private JButton btn_hotel_add;
    private JLabel lbl_hotel_add;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTabbedPane tabbedPane3;
    private JTable tbl_facilities;
    private JTable tbl_pension_types;
    private JTable tbl_seasons;
    private JPanel pnl;
    private JPanel pnl_1;
    private JButton btn_add_season;
    private JButton btn_add_pension_type;
    private JTable tbl_room;
    private JButton btn_add_room;
    private JTextField txtf_hotel_name;
    private JTextField txtf_city;
    private JFormattedTextField txtf_entry;
    private JFormattedTextField txtf_release;
    private JTextField txtf_adult;
    private JButton btn_search;
    private JTextField txtf_children;
    private JButton btn_clean;
    private JButton btn_reservation;
    private JTable tbl_reservation;
    private JButton btn_reservation_update;
    private JButton btn_reservation_delete;
    private JScrollPane sclr_reservation;
    private JButton btn_add_update;
    private DefaultTableModel tmdl_hotels = new DefaultTableModel();
    DefaultTableModel mdl_room = new DefaultTableModel();
    DefaultTableModel mdl_FacilitiesMdl = new DefaultTableModel();
    DefaultTableModel mdl_PensionTypesMdl = new DefaultTableModel();
    DefaultTableModel mdl_seasonMdl = new DefaultTableModel();
    DefaultTableModel mdl_reservationMdl = new DefaultTableModel();

    private final HotelManager hotelManager;
    private final FacilityManager facilityManager;
    private final PensionManager pensionManager;
    private final RoomManager roomManager;
    private Object[] col_room;
    private SeasonManager seasonManager;
    private ReservationManager reservationManager;
    private Room room;

    // Constructor for EmployeeGUI
    public EmployeeGUI(User user) {
        Hotel hotel = new Hotel();
        this.facilityManager = new FacilityManager();
        this.pensionManager = new PensionManager();
        this.hotelManager = new HotelManager();
        this.roomManager = new RoomManager();
        this.seasonManager = new SeasonManager();
        this.reservationManager = new ReservationManager();
        this.add(container);
        this.guiInitialize(2000, 1000);

        if (user == null) {
            dispose();
        }
        this.lbl_employee_welcome.setText("Welcome Back :  " + Helper.firstWordUpper(user.getFullName()));

        // Hotel Management
        loadHotelsTable();
        loadHotelComponents();

        // Room Management
        loadRoomsTable();
        loadRoomComponent();

        // Reservation Management
        loadReservationTable();
        loadReservationComponents();
        btn_employee_logout.addActionListener(e -> {
            LoginGUI loginGUI = new LoginGUI();
            dispose();
        });
    }

    // Hotel Table
    private void loadHotelsTable() {
        Object[] col_hotel_list = {"ID", "Name", "City", "Region", "Full Address", "Phone", "Email", "Star"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel_list.length);
        this.createTable(this.tmdl_hotels, this.tbl_hotels, col_hotel_list, hotelList);
    }

    // Facility Table
    private void loadFacilityTable() {
        Object[] col_facility_list = {"Facility Name"};
        int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
        if (selectedHotelId != -1) {
            ArrayList<Object[]> facilityList = this.facilityManager.getForTableHotelFacility(col_facility_list.length, selectedHotelId);
            this.createTable(this.mdl_FacilitiesMdl, this.tbl_facilities, col_facility_list, facilityList);
            this.tbl_facilities.getColumnModel().getColumn(0).setMaxWidth(200);
        } else {
            this.mdl_FacilitiesMdl.setRowCount(0);
        }
    }

    // Pension Type Table
    private void loadPensionTypeTable() {
        int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
        if (selectedHotelId != -1) {
            Object[] col_pension_types = {"Pension Type"};
            ArrayList<Object[]> pensionTypeList = this.pensionManager.getForTableHotelPensions(col_pension_types.length, selectedHotelId);
            this.createTable(this.mdl_PensionTypesMdl, this.tbl_pension_types, col_pension_types, pensionTypeList);
        } else {
            // Eğer bir otel seçilmemişse, tabloyu temizle
            this.mdl_PensionTypesMdl.setRowCount(0);
        }
    }

    // Season Table
    private void loadSeasonTable() {
        int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
        if (selectedHotelId != -1) {
            Object[] col_pension_types = {"Season"};
            ArrayList<Object[]> pensionTypeList = this.seasonManager.getForTableHotelPensions(col_pension_types.length, selectedHotelId);
            this.createTable(this.mdl_seasonMdl, this.tbl_seasons, col_pension_types, pensionTypeList);
        } else {
            // Eğer bir otel seçilmemişse, tabloyu temizle
            this.mdl_seasonMdl.setRowCount(0);
        }
    }

    // Hotel Components
    private void loadHotelComponents() {
        reSizeComponent();
        this.tbl_hotels.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotels.rowAtPoint(e.getPoint());
                tbl_hotels.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        btn_hotel_add.addActionListener(e -> {
            HotelAddGUI hotelAddUpdateGUI = new HotelAddGUI();
            hotelAddUpdateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelsTable();
                    reSizeComponent();
                }
            });
        });

        btn_hotel_update.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
            if (selectedHotelId != -1) {
                HotelUpdateGUI hotelUpdateGUI = new HotelUpdateGUI(this.hotelManager.getById(selectedHotelId));
                hotelUpdateGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelsTable();
                        reSizeComponent();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_hotel_delete.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
            if (selectedHotelId != -1) {
                if (Helper.confirm("sure", "Delete")) {
                    if (this.hotelManager.delete(selectedHotelId)) {
                        Helper.showMsg("done");
                        loadHotelsTable();
                        reSizeComponent();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        tbl_hotels.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFacilityTable();
                loadPensionTypeTable();
                loadSeasonTable();
            }
        });
        btn_add_pension_type.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
            if (selectedHotelId != -1) {
                PensionGUI pensionGUI = new PensionGUI(this.hotelManager.getById(selectedHotelId));
                pensionGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelsTable();
                        reSizeComponent();
                        dispose();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Room Table
    private void loadRoomsTable() {
        Object[] col_room_list = {"Room ID", "Hotel ID", "Hotel Name", "City", "Room Type", "Room Stock", "Adult Price", "Child Price", "Bed Capacity", "Square Meter", "Television", "Minibar", "Game Console", "Cash Box", "Projection", "Pension ID", "Season ID"};
        ArrayList<Object[]> roomList = this.roomManager.getForTable(col_room_list.length);
        this.createTable(this.mdl_room, this.tbl_room, col_room_list, roomList);
        loadHotelsTable();
    }

    // Method to load room components
    private void loadRoomComponent() {
        btn_add_room.addActionListener(e -> {
            ADDRoomGUI addRoomGUI = new ADDRoomGUI();
            addRoomGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomsTable();
                }
            });
        });

        btn_clean.addActionListener(e -> {
            this.txtf_hotel_name.setText("");
            this.txtf_city.setText("");
            this.txtf_entry.setText("");
            this.txtf_release.setText("");

            loadRoomsTable();
        });

        btn_reservation.addActionListener(e -> {
            int selectedRoomId = this.getTableSelectedRow(tbl_room, 0);
            int selectedHotelId = this.getTableSelectedRow(tbl_room, 1);
            int selectedPensionId = this.getTableSelectedRow(tbl_room, 15);
            int selectedSeasonId = this.getTableSelectedRow(tbl_room, 16);
            if (selectedRoomId != -1) {
                AddReservationGUI addReservationGUI = new AddReservationGUI(this.roomManager.getById(selectedRoomId), this.hotelManager.getById(selectedHotelId), this.pensionManager.getById(selectedPensionId), this.seasonManager.getById(selectedSeasonId));
                addReservationGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRoomsTable();
                        loadReservationTable();
                        loadReservationComponents();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_search.addActionListener(e -> {
            this.tbl_room.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int selectedRow = tbl_room.rowAtPoint(e.getPoint());
                    tbl_room.setRowSelectionInterval(selectedRow, selectedRow);
                }
            });

            String hotelName = txtf_hotel_name.getText().trim();
            String city = txtf_city.getText().trim();
            String entryDate = txtf_entry.getText().trim();
            String releaseDate = txtf_release.getText().trim();

            ArrayList<Room> searchResults = roomManager.searchRooms(hotelName, city, entryDate, releaseDate);
            roomManager.fillRoomTable(searchResults, tbl_room);
        });
    }

    // Reservation Table
    private void loadReservationTable() {
        Object[] col_reservation_list = {"R.ID", "Room ID", "Guest Name", "Guest ID", "Email", "Phone", "Adult", "Child", "Check-in", "Checkout", "Note", "Total Price", "Hotel Id"};
        ArrayList<Object[]> reservationList = this.reservationManager.getForTable(col_reservation_list.length);
        this.createTable(this.mdl_reservationMdl, this.tbl_reservation, col_reservation_list, reservationList);
    }
    // Reservation Components
    private void loadReservationComponents() {
        reSizeComponent();
        this.tbl_reservation.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_reservation.rowAtPoint(e.getPoint());
                tbl_reservation.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        btn_reservation_delete.addActionListener(e -> {
            int selectedReservationId = this.getTableSelectedRow(tbl_reservation, 0);
            int selectedRoomId = this.getTableSelectedRow(tbl_reservation, 1);
            if (selectedReservationId != -1) {
                if (Helper.confirm("sure", "Delete")) {
                    if (this.reservationManager.delete(selectedReservationId)) {
                        Helper.showMsg("done");

                        // Rezervasyon silindiğinde odaya ait stok miktarını artır
                        Room room = this.roomManager.getById(selectedRoomId);
                        room.setStock(room.getStock() + 1);
                        this.roomManager.updateStock(room);

                        loadReservationTable();
                        loadRoomsTable();
                        reSizeComponent();
                    } else {
                        Helper.showMsg("error");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a Reservation.", "No Reservation Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_reservation_update.addActionListener(e -> {
            int selectedRoomId = this.getTableSelectedRow(tbl_reservation, 1);
            int selectedHotelId = this.getTableSelectedRow(tbl_reservation, 12);
            int selectedPensionId = this.getTableSelectedRow(tbl_room, 15);
            int selectedSeasonId = this.getTableSelectedRow(tbl_room, 16);
            int selectedReservationId = this.getTableSelectedRow(tbl_reservation, 0);
            if (selectedRoomId != -1) {
                UpdateReservationGUI updateReservationGUI = new UpdateReservationGUI(this.roomManager.getById(selectedRoomId), this.hotelManager.getById(selectedHotelId), this.pensionManager.getById(selectedPensionId), this.seasonManager.getById(selectedSeasonId), this.reservationManager.getById(selectedReservationId));
                updateReservationGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRoomsTable();
                        loadReservationTable();
                        loadReservationComponents();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        tbl_hotels.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFacilityTable();
                loadPensionTypeTable();
                loadSeasonTable();
            }
        });

        btn_add_season.addActionListener(e -> {
            int selectedHotelId = this.getTableSelectedRow(tbl_hotels, 0);
            if (selectedHotelId != -1) {
                SeasonGUI seasonGUI = new SeasonGUI(this.hotelManager.getById(selectedHotelId));
                seasonGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadHotelsTable();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(EmployeeGUI.this, "Please select a hotel.", "No Hotel Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // General Management
    public void reSizeComponent() {
        this.tbl_hotels.setPreferredSize(new Dimension(tbl_hotels.getWidth(), 500));
        this.tbl_hotels.revalidate();
        this.tbl_hotels.repaint();

        this.tbl_hotels.getColumnModel().getColumn(0).setMaxWidth(40);
        this.tbl_hotels.getColumnModel().getColumn(1).setMaxWidth(180);
        this.tbl_hotels.getColumnModel().getColumn(2).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(3).setMaxWidth(100);
        this.tbl_hotels.getColumnModel().getColumn(4).setMaxWidth(450);
        this.tbl_hotels.getColumnModel().getColumn(5).setMaxWidth(150);
        this.tbl_hotels.getColumnModel().getColumn(6).setMaxWidth(200);
        this.tbl_hotels.getColumnModel().getColumn(7).setMaxWidth(40);
    }
}