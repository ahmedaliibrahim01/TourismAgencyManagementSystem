package core;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class providing helper methods for Swing applications.
 */
public class Helper {

    /**
     * Sets the look and feel of the Swing application to Nimbus, if available.
     */
    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }

    /**
     * Displays a message dialog with a given message string.
     * @param str The message string or a predefined message key.
     */
    public static void showMsg(String str) {
        String message;
        String title;
        switch (str) {
            case "fill" -> {
                message = "Please fill in all fields!";
                title = "Error";
            }
            case "done" -> {
                message = "Successful!";
                title = "Result";
            }
            case "notFound" -> {
                message = "Not found!";
                title = "Not found";
            }
            case "error" -> {
                message = "You've made a mistake!";
                title = "Error!";
            }
            default -> {
                message = str;
                title = "Message";
            }
        }
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays a confirmation dialog with a given message and title.
     * @param str The message string or a predefined message key.
     * @param title The title of the dialog.
     * @return True if the user selects "Yes", false otherwise.
     */
    public static boolean confirm(String str, String title) {
        String msg;
        if (str.equals("sure")) {
            msg = "Are you sure you want to do this?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION) == 0;
    }

    /**
     * Checks if a JTextField is empty.
     * @param field The JTextField to check.
     * @return True if the field is empty, false otherwise.
     */
    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    /**
     * Checks if a JComboBox is empty.
     * @param comboBox The JComboBox to check.
     * @return True if the combo box is empty, false otherwise.
     */
    public static boolean isComboBoxEmpty(JComboBox comboBox) {
        Object selectedItem = comboBox.getSelectedItem();
        if (selectedItem == null) {
            return true;
        } else {
            if (selectedItem instanceof String) {
                return ((String) selectedItem).isEmpty();
            } else {
                return false; // I didn't handle other cases in the example, returned false here without any action.
            }
        }
    }

    /**
     * Checks if a JTable is empty.
     * @param table The JTable to check.
     * @return True if the table is empty, false otherwise.
     */
    public static boolean isTableEmpty(JTable table) {
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                Object value = table.getValueAt(row, col);
                if (value != null && !value.toString().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if an array of JTextFields contains any empty fields.
     * @param fieldList The array of JTextFields to check.
     * @return True if any field is empty, false otherwise.
     */
    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    /**
     * Calculates the location point for a window to be centered on the screen.
     * @param type The type of location point (x or y).
     * @param size The size of the window.
     * @return The location point value.
     */
    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    /**
     * Converts the first character of a string to uppercase.
     * @param str The input string.
     * @return The string with the first character in uppercase.
     */
    public static String firstWordUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
