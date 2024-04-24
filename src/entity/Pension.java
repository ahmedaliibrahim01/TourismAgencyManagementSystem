package entity;

import core.ComboItem;

public class Pension {
    private int pension_id;
    private int hotel_id;
    private String pension_type;

    // Boş kurucu metot
    public Pension() {
    }

    // Parametreli kurucu metot

    public Pension(int pension_id, int hotel_id, String pension_type) {
        this.pension_id = pension_id;
        this.hotel_id = hotel_id;
        this.pension_type = pension_type;
    }


    // Getter ve Setter metotları


    public int getPension_id() {
        return pension_id;
    }

    public void setPension_id(int pension_id) {
        this.pension_id = pension_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getPension_type() {
        return pension_type;
    }

    public void setPension_type(String pension_type) {
        this.pension_type = pension_type;
    }

    public ComboItem getComboItem(){
        return new ComboItem(this.getPension_id(),this.getPension_type());}

    @Override
    public String toString() {
        return pension_type;
    }
}
