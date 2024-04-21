package dao;

import core.Db;
import entity.Facility;
import entity.Hotel;
import entity.PensionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PensionTypeDAO {
    private Connection connection;
    public PensionTypeDAO() {
        this.connection = Db.getInstance();
    }

    public ArrayList<PensionType> findAll() {
        ArrayList<PensionType> pensionTypes = new ArrayList<>();
        String query = "SELECT * FROM public.pension_type ORDER BY pension_type_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                pensionTypes.add(this.match(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return pensionTypes;
    }

    public ArrayList<String> getHotelPensions(int hotelId){
        ArrayList<String> pensionsList = new ArrayList<>();
        try {
            String query = "SELECT hotel_pension_types FROM hotel WHERE hotel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Array pensionArray = resultSet.getArray("hotel_pension_types");

                String[] pensionTypes = (String[]) pensionArray.getArray();
                pensionsList.addAll(Arrays.asList(pensionTypes));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return pensionsList;
    }
    public PensionType match(ResultSet rs) throws SQLException {
        PensionType obj = new PensionType();
        obj.setName(rs.getString("pension_type_name"));
        return obj;
    }
}
