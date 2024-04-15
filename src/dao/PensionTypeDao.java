package dao;

import core.Db;
import entity.PensionType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PensionTypeDao {
    private Connection connection;
    public PensionTypeDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<PensionType> findAll() {
        ArrayList<PensionType> pensionTypeList = new ArrayList<>();
        String sql = "SELECT * FROM public.pension_type ORDER BY pension_type_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                pensionTypeList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pensionTypeList;
    }

    public PensionType match(ResultSet rs) throws SQLException {
        PensionType obj = new PensionType();
        obj.setName(rs.getString("pension_type_name"));
        return obj;
    }
}
