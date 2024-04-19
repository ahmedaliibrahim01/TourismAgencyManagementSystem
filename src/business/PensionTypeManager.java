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
    public ArrayList<PensionType> findAll(Hotel hotel) {
        return this.pensionTypeDao.findAll(hotel);
    }

    public ArrayList<PensionType> getPensionInfo(Hotel hotel){
        return this.pensionTypeDao.getPensionInfo(hotel);
    }

    public ArrayList<Object[]> getForTablePension(int size, Hotel hotel) {
        ArrayList<Object[]> facilityRowList = new ArrayList<>();
        for (PensionType obj : this.findAll(hotel)) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getName();
            facilityRowList.add(rowObject);
        }
        return facilityRowList;
    }
    public boolean savePension(Hotel hotel, String val) {
        if (hotel.getId() != 0) {
            // Otel ID'si 0 olmayan bir pansiyon kaydı oluşturulduğunda başarılı mesajı gösterilir.
            Helper.showMsg("done");
        }
        return this.pensionTypeDao.savePension(hotel, val);
    }
}
