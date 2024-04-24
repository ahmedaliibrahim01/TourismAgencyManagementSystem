package dao;

import core.Db;
import entity.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

// Class responsible for database operations related to reservations
public class ReservationDao {

    private final Connection connection;

    public ReservationDao() {
        this.connection = Db.getInstance();
    }

    // Method to retrieve reservations with a specific hotel ID
    public ArrayList<Reservation> getReservationByOtelId(int otelId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM public.reservation WHERE room_id = ?";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, otelId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Reservation reservation = match(rs);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Method to retrieve a reservation by its ID
    public Reservation getByID(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE reservation_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // Helper method to map ResultSet to Reservation object
    public Reservation match(ResultSet rs) throws SQLException {
        Reservation obj = new Reservation();
        obj.setId(rs.getInt("reservation_id"));
        obj.setRoom_id(rs.getInt("room_id"));
        obj.setGuest_name(rs.getString("guest_name"));
        obj.setGuest_id(rs.getString("guest_id"));
        obj.setGuest_email(rs.getString("guest_email"));
        obj.setGuest_phone(rs.getString("guest_phone"));
        obj.setAdult_count(rs.getInt("adult_count"));
        obj.setChild_count(rs.getInt("child_count"));
        obj.setCheck_in_date(LocalDate.parse(rs.getString("check_in_date")));
        obj.setCheck_out_date(LocalDate.parse(rs.getString("check_out_date")));
        obj.setGuest_note(rs.getString("guest_note"));
        obj.setTotal_price(rs.getDouble("total_price"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        return obj;
    }

    // Method to retrieve all reservations
    public ArrayList<Reservation> findAll() {
        ArrayList<Reservation> resList = new ArrayList<>();
        String sql = "SELECT * FROM public.reservation";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {

                resList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    // Method to add a reservation
    public boolean save(Reservation reservation){
        String query = "INSERT INTO public.reservation" +
                "(room_id," +
                "guest_name," +
                "guest_id," +
                "guest_email," +
                "guest_phone," +
                "adult_count," +
                "child_count," +
                "check_in_date," +
                "check_out_date," +
                "guest_note," +
                "total_price," +
                "hotel_id)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);

            pr.setInt(1,reservation.getRoom_id());
            pr.setString(2,reservation.getGuest_name());
            pr.setString(3,reservation.getGuest_id());
            pr.setString(4,reservation.getGuest_email());
            pr.setString(5,reservation.getGuest_phone());
            pr.setInt(6,reservation.getAdult_count());
            pr.setInt(7,reservation.getChild_count());
            pr.setDate(8,Date.valueOf(reservation.getCheck_in_date()));
            pr.setDate(9,Date.valueOf(reservation.getCheck_out_date()));
            pr.setString(10,reservation.getGuest_note());
            pr.setDouble(11,reservation.getTotal_price());
            pr.setInt(12,reservation.getHotel_id());
            return  pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to delete a reservation
    public boolean delete(int reservation_id){
        try{
            String query = "DELETE FROM public.reservation WHERE reservation_id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,reservation_id);
            return pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to retrieve reservations by a specific reservation ID
    public ArrayList<Reservation> getByListReservationId(int id){
        return this.selectByQuery("SELECT * FROM public.reservation WHERE id="+id);
    }

    // Method to retrieve reservations with a specific query
    public ArrayList<Reservation> selectByQuery(String query){
        ArrayList<Reservation> resList=new ArrayList<>();
        try {
            ResultSet rs=this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                resList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resList;
    }

    // Method to update a reservation
    public boolean update (Reservation reservation){
        try{
            String query = "UPDATE public.reservation SET " +
                    "room_id = ?,"+
                    "check_in_date = ?," +
                    "total_price = ?,"+
                    "guest_count = ?,"+
                    "guest_name = ?,"+
                    "guest_citizen_id = ?,"+
                    "guest_mail = ?,"+
                    "guest_phone = ?,"+
                    "check_out_date = ?"+
                    "WHERE id = ?";

            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,reservation.getRoom_id());
            pr.setString(5,reservation.getGuest_name());
            pr.setString(6,reservation.getGuest_id());
            pr.setString(7,reservation.getGuest_email());
            pr.setString(8,reservation.getGuest_phone());
            pr.setInt(4,reservation.getAdult_count());
            pr.setInt(4,reservation.getChild_count());
            pr.setDate(2,Date.valueOf(reservation.getCheck_in_date()));
            pr.setDate(9,Date.valueOf(reservation.getCheck_out_date()));
            pr.setString(4,reservation.getGuest_note());
            pr.setDouble(3,reservation.getTotal_price());
            pr.setInt(10,reservation.getId());

            return pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }
}
