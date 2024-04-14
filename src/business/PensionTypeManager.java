package business;

import dao.PensionTypeDao;
import entity.PensionType;

import java.util.ArrayList;

public class PensionTypeManager {
    private PensionTypeDao pensionTypeDao;

    public PensionTypeManager() {
        this.pensionTypeDao = new PensionTypeDao();
    }
    public ArrayList<PensionType> findAll() {
        return this.pensionTypeDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> pensionTypeRowList = new ArrayList<>();
        for (PensionType obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            pensionTypeRowList.add(rowObject);
        }
        return pensionTypeRowList;
    }
}
