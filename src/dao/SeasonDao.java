package dao;

import core.Db;
import entity.Pension;
import entity.Season;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

// Class responsible for database operations related to seasons
public class SeasonDao {
    private final Connection connection;

    // Constructor method
    public SeasonDao() {
        this.connection = Db.getInstance();
    }

    // Method to retrieve seasons of a specific hotel
    public ArrayList<Season> getSeasonsByOtelId(int hotelId) {
        ArrayList<Season> seasons = new ArrayList<>();
        String query = "SELECT * FROM public.hotel_season WHERE hotel_id = ?";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Season season = match(rs);
                seasons.add(season);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seasons;
    }

    // Method to get a season by its ID
    public Season getByID(int id) {
        Season obj = null;
        String query = "SELECT * FROM public.hotel_season WHERE season_id = ? ";
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

    // Helper method to map ResultSet to Season object
    public Season match(ResultSet rs) throws SQLException {
        Season obj = new Season();
        obj.setId(rs.getInt("season_id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setStart_date(LocalDate.parse(rs.getString("start_date")));
        obj.setFinish_date(LocalDate.parse(rs.getString("finish_date")));
        return obj;
    }

    // Method to retrieve all seasons
    public ArrayList<Season> findAll() {
        ArrayList<Season> seasonList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_season";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                seasonList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonList;
    }

    // Method to save a season
    public boolean save(Season season){
        String query = "INSERT INTO public.hotel_season"+
                "("+
                "hotel_id,"+
                "start_date," +
                "finish_date"+
                ")"+
                "VALUES (?,?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,season.getHotel_id());
            pr.setDate(2, Date.valueOf(season.getStart_date()));
            pr.setDate(3, Date.valueOf(season.getFinish_date()));
            return  pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to delete a season
    public boolean delete(int hotel_id){
        try{
            String query = "DELETE FROM public.hotel_season WHERE id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1,hotel_id);
            return pr.executeUpdate() != -1;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return true;
    }

    // Method to get seasons of a hotel
    public ArrayList<Season> getHotelSeason(int hotelId) {
        ArrayList<Season> seasonList = new ArrayList<>();
        try {
            String query = "SELECT start_date,finish_date FROM public.hotel_season WHERE hotel_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Season season = new Season();
                season.setStart_date(LocalDate.parse(resultSet.getString("start_date")));
                season.setFinish_date(LocalDate.parse(resultSet.getString("finish_date")));
                seasonList.add(season);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return seasonList;
    }
}
