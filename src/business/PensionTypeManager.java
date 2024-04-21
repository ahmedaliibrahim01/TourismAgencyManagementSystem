package business;

import core.Helper;
import dao.PensionTypeDAO;
import entity.Facility;
import entity.Hotel;
import entity.PensionType;

import java.util.ArrayList;

public class PensionTypeManager {
    private PensionTypeDAO pensionTypeDao;

    public PensionTypeManager() {
        this.pensionTypeDao = new PensionTypeDAO();
    }
    public ArrayList<PensionType> findAll() {
        return this.pensionTypeDao.findAll();
    }

    public ArrayList<String> getHotelPension(int hotelId){
        return this.pensionTypeDao.getHotelPensions(hotelId);
    }

    public ArrayList<Object[]> getForTableHotelPensions(int size, int hotelId){
        ArrayList<Object[]> pensionRowList = new ArrayList<>();
        for (String pensionType : this.getHotelPension(hotelId)){
            Object[] rowObject = new Object[size];
            rowObject[0] = pensionType;
            pensionRowList.add(rowObject);
        }
        return pensionRowList;
    }

    public ArrayList<Object[]> getForTablePensions(int size){
        ArrayList<Object[]> pensionTypeRowList = new ArrayList<>();
        for (PensionType obj : this.findAll()){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getName();
            pensionTypeRowList.add(rowObject);
        }
        return pensionTypeRowList;
    }

}
