package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

// Class responsible for database operations related to pensions
public class PensionDao {
    private final Connection connection;

    // Constructor
    public PensionDao() {
        this.connection = Db.getInstance();
    }

    // Method to get pensions with a specific hotel ID
    public ArrayList<Pension> getPensionByOtelId(int id) {
        ArrayList<Pension> pensions = new ArrayList<>();
        String query = "SELECT * FROM public.hotel_pension WHERE hotel_id = ?";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Pension pension = match(rs);
                pensions.add(pension);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pensions;
    }

    // Method to get a pension by its ID
    public Pension getByID(int id) {
        Pension obj = null;
        String query = "SELECT * FROM public.hotel_pension WHERE pension_id = ? ";
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

    // Helper method to map ResultSet to Pension object
    public Pension match(ResultSet rs) throws SQLException {
        Pension obj = new Pension();
        obj.setPension_id(rs.getInt("pension_id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setPension_type(rs.getString("pension_type"));
        return obj;
    }

    // Method to update a pension
    public boolean update(Pension pension) {
        try {
            String query = "UPDATE public.hotel_pension SET " +
                    "hotel_id = ?," +
                    "pension_type = ?" +
                    "WHERE user_id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,pension.getHotel_id());
            pr.setString(2,pension.getPension_type());
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to get all pensions
    public ArrayList<Pension> findAll() {
        ArrayList<Pension> pensionList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_pension";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                pensionList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionList;
    }

    // Method to add a pension
    public boolean save(Pension pension){
        String query = "INSERT INTO public.hotel_pension"+
                "("+
                "hotel_id,"+
                "pension_type"+
                ")"+
                "VALUES (?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,pension.getHotel_id());
            pr.setString(2,pension.getPension_type());
            return  pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to delete a pension
    public boolean delete(int hotel_id){
        try{
            String query = "DELETE FROM public.hotel_pension WHERE pension_id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,hotel_id);
            return pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to get pension types of a hotel
    public ArrayList<Pension> getHotelPensionTypes(int hotelId) {
        ArrayList<Pension> pensionTypeList = new ArrayList<>();
        try {
            String query = "SELECT pension_type FROM public.hotel_pension WHERE hotel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pension pension = new Pension();
                pension.setPension_type(resultSet.getString("pension_type"));
                pensionTypeList.add(pension);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionTypeList;
    }
}
