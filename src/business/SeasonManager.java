package business;

import core.Helper;
import dao.SeasonDao;
import entity.Season;

import java.util.ArrayList;

// Manages operations related to seasons
public class SeasonManager {
    SeasonDao seasonDao = new SeasonDao();

    // Retrieves a season by its ID
    public Season getById(int id){
        return this.seasonDao.getByID(id);
    }

    // Retrieves seasons by a specific hotel ID
    public ArrayList<Season> getSeasonsByOtelId(int id){
        return this.seasonDao.getSeasonsByOtelId(id);}

    // Retrieves all seasons
    public ArrayList<Season> findAll() {
        return this.seasonDao.findAll();
    }

    // Provides information necessary for the table
    public ArrayList<Object[]> getForTable(int size, ArrayList<Season> seasons){
        ArrayList<Object[]> seasonList = new ArrayList<>();
        for (Season obj : seasons){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getFinish_date();
            seasonList.add(rowObject);
        }
        return seasonList;
    }

    // Adds a season record to the database
    public boolean save(Season season){
        if(season.getId()!=0){
            Helper.showMsg("error");
        }
        return this.seasonDao.save(season);
    }

    // Deletes a season with a specific ID
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id + " ID registered model not found");
            return false;
        }
        return this.seasonDao.delete(id);
    }

    // Retrieves seasons for a specific hotel
    public ArrayList<Season> getHotelSeasons(int hotelId){
        return this.seasonDao.getHotelSeason(hotelId);
    }

    // Provides information necessary for the table of hotel pensions
    public ArrayList<Object[]> getForTableHotelPensions(int size, int hotelId) {
        ArrayList<Object[]> seasonRowList = new ArrayList<>();
        for (Season season : this.getHotelSeasons(hotelId)){
            Object[] rowObject = new Object[size];
            rowObject[0] = season;
            seasonRowList.add(rowObject);
        }
        return seasonRowList;
    }
}
