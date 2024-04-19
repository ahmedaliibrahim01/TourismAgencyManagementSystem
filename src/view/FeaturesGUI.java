package view;

import business.PensionTypeManager;
import core.Helper;
import entity.Hotel;
import entity.PensionType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FeaturesGUI extends Layout{
    private JPanel container;
    private JTabbedPane tabbedPane1;
    private JTabbedPane tabbedPane2;
    private JTable tbl_py_right;
    private JTable tbl_py_left;
    private JButton btn_add;
    private JButton btn_remove;
    private JButton btn_save;
    DefaultTableModel unselectedFacilitiesMdl = new DefaultTableModel();
    private String[] unselectedFacilities = {
            "Ücretsiz Otopark",
            "Ücretsiz WiFi",
            "Yüzme Havuzu",
            "Fitness Center",
            "Hotel Concierge",
            "SPA",
            "7/24 Oda Servisi"
    };
    DefaultTableModel selectedFacilitiesMdl = new DefaultTableModel();
    private String[] selectedFacilities = {};
    private PensionTypeManager pensionTypeManager;
    private Hotel hotel;

    public FeaturesGUI(Hotel hotel) {
        this.hotel = hotel;
        this.pensionTypeManager = new PensionTypeManager();
        this.add(container);
        this.guiInitilaze(500, 720);

        if (this.hotel == null){
            dispose();
        }



        // Pension Type Management
        loadLeftTable();
        loadRightTable();
        loadComponent();
    }

    private void loadUsersTable() {
        Object[] col_pension_list = {"Pension Type Name"};
        ArrayList<Object[]> pensionList = this.pensionTypeManager.getForTablePension(col_pension_list.length, this.hotel);
        this.createTable(this.unselectedFacilitiesMdl, this.tbl_py_right, col_pension_list, pensionList);
    }

    private void loadLeftTable() {
        unselectedFacilitiesMdl.addColumn("Pension Type Name");
        for(String facility : unselectedFacilities){
            unselectedFacilitiesMdl.addRow(new Object[]{facility});
        }
        tbl_py_left.setModel(unselectedFacilitiesMdl);
    }

    private void loadRightTable() {
        selectedFacilitiesMdl.addColumn("Pension Type Name");
        for(String pension : selectedFacilities){
            selectedFacilitiesMdl.addRow(new Object[]{pension});
        }
        tbl_py_right.setModel(selectedFacilitiesMdl);
    }

    private void loadComponent() {
        this.tbl_py_right.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_py_right.rowAtPoint(e.getPoint());
                tbl_py_right.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
        this.tbl_py_left.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_py_left.rowAtPoint(e.getPoint());
                tbl_py_left.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        btn_add.addActionListener(e -> {
            int selectedRow = tbl_py_left.getSelectedRow();
            if (selectedRow != -1){
                Object[] rowData = {tbl_py_left.getValueAt(selectedRow,0)};
                selectedFacilitiesMdl.addRow(rowData);
                String pensionName = (String) rowData[0];
                selectedFacilities = addFacilityToArray(selectedFacilities, pensionName);
                unselectedFacilitiesMdl.removeRow(selectedRow);
            }
        });

        btn_remove.addActionListener(e -> {
            int selectedRow = tbl_py_right.getSelectedRow();
            if (selectedRow != -1){
                Object[] rowData = {tbl_py_right.getValueAt(selectedRow,0)};
                unselectedFacilitiesMdl.addRow(rowData);
                String facilityName = (String) rowData[0];
                unselectedFacilities = addFacilityToArray(unselectedFacilities, facilityName);
                selectedFacilitiesMdl.removeRow(selectedRow);
            }
        });

        btn_save.addActionListener(e -> {
            if (Helper.isTableEmpty(this.tbl_py_right)){
                Helper.showMsg("fill");
            } else {
                boolean result = true;
                if (this.tbl_py_right == null){
                    for (String pension : selectedFacilities){
                        // İlgili otel ve pansiyon tipi bilgileriyle savePension yöntemini çağırın
                        result &= this.pensionTypeManager.savePension(hotel, pension);
                    }
                } else {
                    for (String pension : selectedFacilities){
                        this.tbl_py_right.getValueAt(0,0);
                        this.pensionTypeManager.savePension(hotel, pension);
                    }
                }
                if (result) {
                    Helper.showMsg("Saved successfully!");
                } else {
                    Helper.showMsg("An error occurred while saving.");
                }
            }
        });
    }
    private String[] addFacilityToArray(String[] selectedFacilities, String facilityName) {
        String[] newFacilities = new String[selectedFacilities.length + 1];
        System.arraycopy(selectedFacilities, 0, newFacilities, 0, selectedFacilities.length);
        newFacilities[newFacilities.length - 1] = facilityName;
        return newFacilities;
    }
}
