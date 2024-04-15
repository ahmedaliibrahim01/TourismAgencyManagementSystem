package view;

import core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Layout extends JFrame {
    public void guiInitilaze(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Tourism Agency System");
        this.setSize(width, height);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));
        this.setVisible(true);
    }
    public void createTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows) {
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.setEnabled(false);

        DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
        clearModel.setRowCount(0);

        if (rows == null) {
            rows = new ArrayList<>();
        }

        for (Object[] row : rows) {
            model.addRow(row);
        }
    }

    int getTableSelectedRow(JTable table, int col_index){
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (int) table.getModel().getValueAt(selectedRow, col_index);
        }
        return -1;
    }

    public void tableRowSelect(JTable table){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    public void printFrameSize(JFrame frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }
    public void printFrameSize(JPanel frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }
    public void printFrameSize(JTextField frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }

}
