package view;

import core.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// Represents the layout of the application
public class Layout extends JFrame {

    // Initializes the GUI window
    public void guiInitialize(int width, int height) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Tourism Agency System");
        this.setSize(width, height);
        this.setLocation(Helper.getLocationPoint("x", this.getSize()), Helper.getLocationPoint("y", this.getSize()));
        this.setVisible(true);
    }

    // Creates a table with given model, columns, and rows
    public void createTable(DefaultTableModel model, JTable table, Object[] columns, ArrayList<Object[]> rows) {
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
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

    // Retrieves the ID of the selected row in the table
    int getTableSelectedRow(JTable table, int col_index){
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (int) table.getModel().getValueAt(selectedRow, col_index);
        }
        return -1;
    }

    // Selects the entire row when clicked in the table
    public void tableRowSelect(JTable table){
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selected_row = table.rowAtPoint(e.getPoint());
                table.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    // Prints the size of JFrame
    public void printFrameSize(JFrame frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }

    // Prints the size of JPanel
    public void printFrameSize(JPanel frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }

    // Prints the size of JTextField
    public void printFrameSize(JTextField frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }

    // Prints the size of JTabbedPane
    public void printFrameSize(JTabbedPane frame) {
        Dimension size = frame.getSize();
        System.out.println("Dimension is: " + size);
    }
}
