package dao;

import core.Db;
import entity.Hotel;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelDao {
    private Connection connection;
    public HotelDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Hotel> findAll() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel ORDER BY hotel_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                hotelList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelList;
    }

    public Hotel match(ResultSet rs) throws SQLException {
        Hotel obj = new Hotel();
        obj.setId(rs.getInt("hotel_id"));
        obj.setName(rs.getString("hotel_name"));
        return obj;
    }
}
