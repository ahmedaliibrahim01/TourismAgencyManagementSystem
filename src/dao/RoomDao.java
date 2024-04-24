package dao;

import core.Db;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

// Class responsible for database operations related to rooms
public class RoomDao {
    private final Connection connection;

    // Constructor method
    public RoomDao() {
        this.connection = Db.getInstance();
    }

    // Method to retrieve all rooms
    public ArrayList<Room> findAll() {
        ArrayList<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_room";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                roomList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Helper method to map ResultSet to Room object
    public Room match(ResultSet rs) throws SQLException {
        Room obj = new Room();
        obj.setRoom_id(rs.getInt("room_id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setHotel_name(rs.getString("hotel_name"));
        obj.setHotel_city(rs.getString("hotel_city"));
        obj.setType(rs.getString("type"));
        obj.setStock(rs.getInt("stock"));
        obj.setAdult_price(rs.getDouble("adult_price"));
        obj.setChild_price(rs.getDouble("child_price"));
        obj.setBed_capacity(rs.getInt("bed_capacity"));
        obj.setSquare_meter(rs.getInt("square_meter"));
        obj.setTelevision(rs.getBoolean("television"));
        obj.setMinibar(rs.getBoolean("minibar"));
        obj.setGame_console(rs.getBoolean("game_console"));
        obj.setCash_box(rs.getBoolean("cash_box"));
        obj.setProjection(rs.getBoolean("projection"));
        obj.setPension_id(rs.getInt("pension_id"));
        obj.setSeason_id(rs.getInt("season_id"));
        return obj;
    }

    // Method to add a room
    public boolean save(Room room) {
        String query = "INSERT INTO public.hotel_room" +
                "(" +
                "hotel_name," +
                "hotel_city,"+
                "type," +
                "stock," +
                "adult_price," +
                "child_price," +
                "bed_capacity," +
                "square_meter," +
                "television," +
                "minibar," +
                "game_console," +
                "cash_box," +
                "projection," +
                "hotel_id," +
                "pension_id," +
                "season_id" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1,room.getHotel_name());
            pr.setString(2,room.getHotel_city());
            pr.setString(3, room.getType());
            pr.setInt(4, room.getStock());
            pr.setDouble(5, room.getAdult_price());
            pr.setDouble(6, room.getChild_price());
            pr.setInt(7, room.getBed_capacity());
            pr.setInt(8, room.getSquare_meter());
            pr.setBoolean(9, room.isTelevision());
            pr.setBoolean(10, room.isMinibar());
            pr.setBoolean(11, room.isGame_console());
            pr.setBoolean(12, room.isCash_box());
            pr.setBoolean(13, room.isProjection());
            pr.setInt(14,room.getHotel_id());
            pr.setInt(15, room.getPension_id());
            pr.setInt(16, room.getSeason_id());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to dynamically create and execute a SQL query with necessary parameters
    public ArrayList<Room> searchRooms(String hotelName, String city, String entryDate, String releaseDate) {
        ArrayList<Room> roomList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM public.hotel_room WHERE 1=1");

        // Add hotel name parameter to the query if provided
        if (hotelName != null && !hotelName.isEmpty()) {
            sqlBuilder.append(" AND hotel_name = ?");
        }

        // Add city parameter to the query if provided
        if (city != null && !city.isEmpty()) {
            sqlBuilder.append(" AND hotel_city = ?");
        }

        // Add entry date parameter to the query if provided
        if (entryDate != null && !entryDate.isEmpty()) {
            sqlBuilder.append(" AND entry_date >= ?");
        }

        // Add release date parameter to the query if provided
        if (releaseDate != null && !releaseDate.isEmpty()) {
            sqlBuilder.append(" AND release_date <= ?");
        }

        try {
            PreparedStatement pr = connection.prepareStatement(sqlBuilder.toString());

            int parameterIndex = 1;

            // Add parameters to the query
            if (hotelName != null && !hotelName.isEmpty()) {
                pr.setString(parameterIndex++, hotelName);
            }
            if (city != null && !city.isEmpty()) {
                pr.setString(parameterIndex++, city);
            }
            if (entryDate != null && !entryDate.isEmpty()) {
                pr.setString(parameterIndex++, entryDate);
            }
            if (releaseDate != null && !releaseDate.isEmpty()) {
                pr.setString(parameterIndex++, releaseDate);
            }

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                roomList.add(match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Method to retrieve a room by its ID
    public Room getById(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.hotel_room WHERE room_id = ? ";
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

    // Method to update the stock information of a room
    public boolean updateStock(Room room){
        String query = "UPDATE public.hotel_room SET stock = ? WHERE room_id = ? ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, room.getStock());
            pr.setInt(2,room.getRoom_id());

            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
