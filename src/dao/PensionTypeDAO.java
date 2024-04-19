package dao;

import core.Db;
import entity.Hotel;
import entity.PensionType;

import java.sql.*;
import java.util.ArrayList;

public class PensionTypeDAO {
    private Connection connection;
    public PensionTypeDAO() {
        this.connection = Db.getInstance();
    }

    public ArrayList<PensionType> getPensionInfo(Hotel hotel){
        ArrayList<PensionType> pensionTypeList = new ArrayList<>();
        String query = "SELECT hotel_facilities FROM hotel WHERE hotel_id = ? ORDER BY pension_type_id ASC";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,hotel.getId());
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                Array facilitiesArray = rs.getArray("hotel_facilities");
                if ( facilitiesArray != null) {
                    String[] facilities = (String[]) facilitiesArray.getArray();
                    // Olanakları yazdır
                    for (int i = 0; i < facilities.length; i++) {
                        String facility = facilities[i];
                        pensionTypeList.add(this.match(rs));
                    }
                } else {
                    // Herhangi bir olanak yoksa mesajı yazdır
                    System.out.println("No facilities available");
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return pensionTypeList;
    }
    public ArrayList<PensionType> findAll(Hotel hotel) {
        ArrayList<PensionType> pensionTypeList = new ArrayList<>();
        String query = "SELECT pension_type_name FROM public.pension_type where hotel_id = ? ORDER BY pension_type_id ASC ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, hotel.getId());
            ResultSet rs = pr.executeQuery(); // Değişiklik burada
            while (rs.next()) {
                PensionType pensionType = new PensionType();
                pensionType.setName(rs.getString("pension_type_name"));
                pensionTypeList.add(pensionType);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pensionTypeList;
    }

    public boolean savePension(Hotel hotel, String val) {
        String query = "INSERT INTO public.pension_type (hotel_id, pension_type_name) VALUES (?, ?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, hotel.getId());
            pr.setString(2, val);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public PensionType match(ResultSet rs) throws SQLException {
        PensionType obj = new PensionType();
        obj.setName(rs.getString("pension_type_name"));
        return obj;
    }
}
