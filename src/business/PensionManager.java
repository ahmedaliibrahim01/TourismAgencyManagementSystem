package business;

import core.Helper;
import dao.PensionDao;
import entity.Pension;

import java.util.ArrayList;

// Manages operations related to pensions
public class PensionManager {
    PensionDao pensionDao = new PensionDao();

    // Retrieves a pension by a specific ID
    public Pension getById(int id){
        return this.pensionDao.getByID(id);
    }

    // Retrieves all pensions
    public ArrayList<Pension> findAll() {
        return this.pensionDao.findAll();
    }

    // Retrieves pensions by a specific hotel ID
    public ArrayList<Pension> getPensionByOtelId(int id){
        return this.pensionDao.getPensionByOtelId(id);
    }

    // Provides information necessary for the table
    public ArrayList<Object[]> getForTable(int size, ArrayList<Pension> pensions){
        ArrayList<Object[]> pensionList = new ArrayList<>();
        for (Pension obj : pensions){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getPension_id();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getPension_type();
            pensionList.add(rowObject);
        }
        return pensionList;
    }

    // Adds a pension record to the database
    public boolean save(Pension pension){
        if(pension.getPension_id()!=0){
            Helper.showMsg("error");
        }
        return this.pensionDao.save(pension);
    }

    // Retrieves pension types for a specific hotel ID
    public ArrayList<Pension> getHotelFacilities(int hotelId){
        return this.pensionDao.getHotelPensionTypes(hotelId);
    }

    // Provides information necessary for the table for hotel pensions
    public ArrayList<Object[]> getForTableHotelPensions(int size, int hotelId) {
        ArrayList<Object[]> facilityRowList = new ArrayList<>();
        for (Pension pensionType : this.getHotelFacilities(hotelId)) {
            Object[] rowObject = new Object[size];
            rowObject[0] = pensionType;
            facilityRowList.add(rowObject);
        }
        return facilityRowList;
    }

    // Deletes a pension by a specific ID
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id + " ID registered model not found");
            return false;
        }
        return this.pensionDao.delete(id);
    }
}
