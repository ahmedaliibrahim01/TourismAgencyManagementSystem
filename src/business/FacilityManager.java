package business;

import dao.FacilityDao;
import entity.Facility;

import java.util.ArrayList;

public class FacilityManager {
    private FacilityDao facilityDao;

    public FacilityManager() {
        this.facilityDao = new FacilityDao();
    }
    public ArrayList<Facility> findAll() {
        return this.facilityDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {
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
