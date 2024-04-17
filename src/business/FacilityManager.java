package business;

import dao.FacilityDao;
import dao.HotelDao;
import entity.Facility;
import entity.Hotel;

import java.util.ArrayList;

public class FacilityManager {
    private FacilityDao facilityDao;

    public FacilityManager() {
        this.facilityDao = new FacilityDao();
    }
    public ArrayList<Facility> findAll() {
        return this.facilityDao.findAll();
    }
    public ArrayList<Object[]> getForTableFacilities(int size) {
        ArrayList<Object[]> facilityRowList = new ArrayList<>();
        for (Facility obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getName();
            facilityRowList.add(rowObject);
        }
        return facilityRowList;
    }
}
