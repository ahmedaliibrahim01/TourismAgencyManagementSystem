package dao;

import core.Db;
import entity.Facility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FacilityDao {
    private Connection connection;
    public FacilityDao() {
        this.connection = Db.getInstance();
    }

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


    public Facility match(ResultSet rs) throws SQLException {
        Facility obj = new Facility();
        obj.setName(rs.getString("facility_name"));
        return obj;
    }
}
