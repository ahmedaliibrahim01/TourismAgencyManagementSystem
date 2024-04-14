package dao;

import core.Db;
import entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public boolean update(Hotel hotel) {
        String query = "UPDATE public.hotel SET hotel_name = ?, hotel_city = ?, hotel_region = ?, hotel_full_address = ?, hotel_phone = ?, hotel_email = ?, hotel_star = ? WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getCity());
            pr.setString(3, hotel.getRegion());
            pr.setString(4, hotel.getFullAddress());
            pr.setString(5, hotel.getPhone());
            pr.setString(6, hotel.getEmail());
            pr.setString(7, hotel.getStar());
            pr.setInt(8,hotel.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(Hotel hotel) {
        String query = "INSERT INTO public.hotel (hotel_name, hotel_city, hotel_region, hotel_full_address, hotel_phone, hotel_email, hotel_star) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getCity());
            pr.setString(3, hotel.getRegion());
            pr.setString(4, hotel.getFullAddress());
            pr.setString(5, hotel.getPhone());
            pr.setString(6, hotel.getEmail());
            pr.setString(7, hotel.getStar());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Hotel getById(int id) {
        Hotel obj = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ? ";
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

    public Hotel match(ResultSet rs) throws SQLException {
        Hotel obj = new Hotel();
        obj.setId(rs.getInt("hotel_id"));
        obj.setName(rs.getString("hotel_name"));
        obj.setCity(rs.getString("hotel_city"));
        obj.setRegion(rs.getString("hotel_region"));
        obj.setFullAddress(rs.getString("hotel_full_address"));
        obj.setPhone(rs.getString("hotel_phone"));
        obj.setEmail(rs.getString("hotel_email"));
        obj.setStar(rs.getString("hotel_star"));
        return obj;
    }
}
