package business;

import core.Helper;
import dao.RoomDao;
import entity.Hotel;
import entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Manages operations related to rooms
public class RoomManager {
    RoomDao roomDao = new RoomDao();
    HotelManager hotelManager = new HotelManager();

    // Adds a room record to the database
    public boolean save(Room room) {
        if (room.getRoom_id() != 0) {
            Helper.showMsg("error");
        }
        return this.roomDao.save(room);
    }

    // Retrieves all rooms
    public ArrayList<Room> findAll() {
        return this.roomDao.findAll();
    }

    // Provides information necessary for the table
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> roomList = new ArrayList<>();
        for (Room obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getRoom_id();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getHotel_name();
            rowObject[i++] = obj.getHotel_city();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getAdult_price();
            rowObject[i++] = obj.getChild_price();
            rowObject[i++] = obj.getBed_capacity();
            rowObject[i++] = obj.getSquare_meter();
            rowObject[i++] = obj.isTelevision();
            rowObject[i++] = obj.isMinibar();
            rowObject[i++] = obj.isGame_console();
            rowObject[i++] = obj.isCash_box();
            rowObject[i++] = obj.isProjection();
            rowObject[i++] = obj.getPension_id();
            rowObject[i++] = obj.getSeason_id();
            roomList.add(rowObject);
        }
        return roomList;
    }

    // Retrieves the city of the hotel
    public String getHotelCity(int hotelId) {
        Hotel hotel = hotelManager.findHotelById(hotelId);
        if (hotel != null) {
            return hotel.getCity();
        } else {
            return ""; // Returns an empty string if the hotel is not found or there is no city information
        }
    }

    // Searches for rooms based on hotel name, city, entry date, and release date
    public ArrayList<Room> searchRooms(String hotelName, String city, String entryDate, String releaseDate) {
        return roomDao.searchRooms(hotelName, city, entryDate, releaseDate);
    }

    // Fills the room table with room information
    public void fillRoomTable(ArrayList<Room> roomList, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Room room : roomList) {
            Object[] row = {
                    room.getRoom_id(),
                    room.getHotel_id(),
                    room.getHotel_name(),
                    room.getHotel_city(),
                    room.getType(),
                    room.getStock(),
                    room.getAdult_price(),
                    room.getChild_price(),
                    room.getBed_capacity(),
                    room.getSquare_meter(),
                    room.isTelevision(),
                    room.isMinibar(),
                    room.isGame_console(),
                    room.isCash_box(),
                    room.isProjection(),
                    room.getPension_id(),
                    room.getSeason_id()
            };
            model.addRow(row);
        }
    }

    // Retrieves a room by its ID
    public Room getById(int id) {
        return this.roomDao.getById(id);
    }

    // Updates the stock of a room
    public boolean updateStock(Room room){
        if(this.getById(room.getRoom_id()) == null){
            return false;
        }
        return this.roomDao.updateStock(room);
    }
}
