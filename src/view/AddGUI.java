package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddGUI extends Layout {
    private JPanel container;
    private JTabbedPane tabbedPane1;
    private JTable tbl_unselected;
    private JTabbedPane tabbedPane2;
    private JTable tbl_selected;
    private JButton btn_add;
    private JButton btn_remove;

    // Otel isimlerinin bulunduğu dizi
    private String[] hotels = {};

    // Tesislerin bulunduğu dizi
    private String[] facilities = {
            "Ücretsiz Otopark",
           " Ücretsiz WiFi",
           "Yüzme Havuzu",
            "Fitness Center",
            "Hotel Concierge",
            "SPA",
            "7/24 Oda Servisi"
    };

    public AddGUI() {
        this.add(container);
        this.guiInitilaze(500, 500);
        container.setPreferredSize(new Dimension(500, 500));

        // Tesislerin tabloya eklenmesi
        DefaultTableModel unselectedModel = new DefaultTableModel();
        unselectedModel.addColumn("Facility Name");
        for (String facility : facilities) {
            unselectedModel.addRow(new Object[]{facility});
        }
        tbl_unselected.setModel(unselectedModel);

        // Otellerin tabloya eklenmesi
        DefaultTableModel selectedModel = new DefaultTableModel();
        selectedModel.addColumn("Hotel Name");
        for (String hotel : hotels) {
            selectedModel.addRow(new Object[]{hotel});
        }
        tbl_selected.setModel(selectedModel);

        // btn_add için ActionListener
        btn_add.addActionListener(e -> {
            // Seçilen satırın indeksini al
            int selectedRow = tbl_unselected.getSelectedRow();
            if (selectedRow != -1) {
                // Seçilen satırın verisini al
                Object[] rowData = {tbl_unselected.getValueAt(selectedRow, 0)};
                // tbl_selected tablosuna satırı ekle
                selectedModel.addRow(rowData);
                // hotels dizisine oteli ekle
                String hotelName = (String) rowData[0];
                hotels = addHotelToArray(hotels, hotelName);
                // tbl_unselected tablosundan satırı kaldır
                unselectedModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(container, "Lütfen eklemek istediğiniz özelliği seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        // btn_remove için ActionListener
        btn_remove.addActionListener(e -> {
            // Seçilen satırın indeksini al
            int selectedRow = tbl_selected.getSelectedRow();
            if (selectedRow != -1) {
                // Seçilen satırın verisini al
                Object[] rowData = {tbl_selected.getValueAt(selectedRow, 0)};
                // tbl_unselected tablosuna satırı ekle
                unselectedModel.addRow(rowData);
                // facilities dizisine tesisi ekle
                String facilityName = (String) rowData[0];
                facilities = addFacilityToArray(facilities, facilityName);
                // tbl_selected tablosundan satırı kaldır
                selectedModel.removeRow(selectedRow);
            }
        });
    }

    // hotels dizisine otel ekleyen metot
    private String[] addHotelToArray(String[] hotels, String hotelName) {
        String[] newHotels = new String[hotels.length + 1];
        System.arraycopy(hotels, 0, newHotels, 0, hotels.length);
        newHotels[newHotels.length - 1] = hotelName;
        return newHotels;
    }

    // facilities dizisine tesis ekleyen metot
    private String[] addFacilityToArray(String[] facilities, String facilityName) {
        String[] newFacilities = new String[facilities.length + 1];
        System.arraycopy(facilities, 0, newFacilities, 0, facilities.length);
        newFacilities[newFacilities.length - 1] = facilityName;
        return newFacilities;
    }
}