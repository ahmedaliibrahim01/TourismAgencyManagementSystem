package business;

import core.Helper;
import dao.HotelDAO;
import entity.Hotel;

import java.util.ArrayList;

public class HotelManager {
    private HotelDAO hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDAO();
    }
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    public boolean update(Hotel hotel) {
        return this.hotelDao.update(hotel);
    }

    public boolean save(Hotel hotel) {
        if (hotel.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.hotelDao.save(hotel);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg("Hotel Not found");
            return false;
        }
        return this.hotelDao.delete(id);
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> hotelRowList = new ArrayList<>();
        for (Hotel obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getCity();
            rowObject[i++] = obj.getRegion();
            rowObject[i++] = obj.getFullAddress();
            rowObject[i++] = obj.getPhone();
            rowObject[i++] = obj.getEmail();
            rowObject[i++] = obj.getStar();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }
    public ArrayList<Object[]> getForTableFacilities(int size) {
        ArrayList<Object[]> facilityRowList = new ArrayList<>();
        for (Hotel obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getFacilities();
            facilityRowList.add(rowObject);
        }
        return facilityRowList;
    }
    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }

}
