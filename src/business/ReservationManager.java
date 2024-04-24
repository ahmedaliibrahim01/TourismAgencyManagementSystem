package business;

import core.Helper;
import dao.ReservationDao;
import entity.Reservation;

import java.util.ArrayList;

// Manages operations related to reservations
public class ReservationManager {
    ReservationDao reservationDao = new ReservationDao();

    // Retrieves a reservation by a specific ID
    public Reservation getById(int id){
        return this.reservationDao.getByID(id);
    }

    // Retrieves reservations by a specific hotel ID
    public ArrayList<Reservation> getReservationByOtelId(int id){
        return this.reservationDao.getReservationByOtelId(id);
    }

    // Retrieves all reservations
    public ArrayList<Reservation> findAll() {
        return this.reservationDao.findAll();
    }

    // Provides information necessary for the table
    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> resList = new ArrayList<>();
        for (Reservation obj : this.findAll()){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getGuest_name();
            rowObject[i++] = obj.getGuest_id();
            rowObject[i++] = obj.getGuest_email();
            rowObject[i++] = obj.getGuest_phone();
            rowObject[i++] = obj.getAdult_count();
            rowObject[i++] = obj.getChild_count();
            rowObject[i++] = obj.getCheck_in_date();
            rowObject[i++] = obj.getCheck_out_date();
            rowObject[i++] = obj.getGuest_note();
            rowObject[i++] = obj.getTotal_price();
            rowObject[i++] = obj.getHotel_id();
            resList.add(rowObject);
        }
        return resList;
    }

    // Adds a reservation record to the database
    public boolean save(Reservation reservation){
        if(reservation.getId()!=0){
            Helper.showMsg("error");
        }
        return this.reservationDao.save(reservation);
    }

    // Deletes a reservation by a specific ID
    public boolean delete(int id){
        if (this.getById(id)==null){
            Helper.showMsg(" Reservation Not found");
            return false;
        }
        return this.reservationDao.delete(id);
    }

    // Updates reservation information
    public boolean update(Reservation reservation){
        if(this.getById(reservation.getId()) == null){
            Helper.showMsg("Reservation " + reservation.getId() + " not found");
            return false;
        }
        return this.reservationDao.update(reservation);
    }
}