package dao;

import core.Db;
import entity.Facility;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

// Class responsible for database operations related to facilities
public class FacilityDAO {
    private Connection connection;

    // Constructor
    public FacilityDAO() {
        this.connection = Db.getInstance();
    }

    // Method to find all facilities
    public ArrayList<Facility> findAll() {
        ArrayList<Facility> facilityList = new ArrayList<>();
        String query = "SELECT * FROM public.facility ORDER BY facility_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                facilityList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilityList;
    }

    // Method to get facilities of a hotel by its ID
    public ArrayList<String> getHotelFacilities(int hotelId) {
        ArrayList<String> facilitiesList = new ArrayList<>();
        try {
            String query = "SELECT hotel_facilities FROM hotel WHERE hotel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Array facilitiesArray = resultSet.getArray("hotel_facilities");

                // Extract facilities from the array and add them to the list
                String[] facilities = (String[]) facilitiesArray.getArray();
                facilitiesList.addAll(Arrays.asList(facilities));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facilitiesList;
    }

    // Helper method to map ResultSet to Facility object
    public Facility match(ResultSet rs) throws SQLException {
        Facility obj = new Facility();
        obj.setName(rs.getString("facility_name"));
        return obj;
    }
}
