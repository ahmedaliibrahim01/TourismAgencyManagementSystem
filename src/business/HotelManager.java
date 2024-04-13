package business;

import dao.HotelDao;
import entity.Hotel;
import entity.User;

import java.util.ArrayList;

public class HotelManager {
    private HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> hotelRowList = new ArrayList<>();
        for (Hotel obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }
}
