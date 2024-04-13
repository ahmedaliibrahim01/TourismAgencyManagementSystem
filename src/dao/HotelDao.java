package dao;

import core.Db;

import java.sql.Connection;

public class HotelDao {
    private Connection connection;
    public HotelDao() {
        this.connection = Db.getInstance();
    }

}
