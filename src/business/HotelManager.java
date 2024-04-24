package business;

import core.Helper;
import dao.HotelDAO;
import entity.Hotel;

import java.util.ArrayList;

// Manages operations related to hotels
public class HotelManager {
    private HotelDAO hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDAO();
    }

    // Retrieves all hotels
    public ArrayList<Hotel> findAll() {
        return this.hotelDao.findAll();
    }

    // Retrieves a hotel by a specific ID
    public Hotel findHotelById(int hotelId){
        return hotelDao.getById(hotelId);
    }

    // Updates a hotel
    public boolean update(Hotel hotel) {
        return this.hotelDao.update(hotel);
    }

    // Adds a hotel record to the database
    public boolean save(Hotel hotel) {
        if (hotel.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.hotelDao.save(hotel);
    }

    // Deletes a hotel by a specific ID
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg("Hotel Not found");
            return false;
        }
        return this.hotelDao.delete(id);
    }

    // Provides information necessary for the table
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

    // Provides information necessary for the facilities table
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

    // Retrieves a hotel by a specific ID
    public Hotel getById(int id) {
        return this.hotelDao.getById(id);
    }
}
