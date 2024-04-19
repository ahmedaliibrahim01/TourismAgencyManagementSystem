package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

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

    public static void showMsg(String str) {
        String Message;
        String title;
        switch (str) {
            case "fill" -> {
                Message = "Please fill in all fields!";
                title = "Error";
            }
            case "done" -> {
                Message = "Successful !";
                title = "Result";
            }
            case "notFound" -> {
                Message = "Not found !";
                title = "Not found";
            }
            case "error" -> {
                Message = "You've made a mistake!";
                title = "Error!";
            }
            default -> {
                Message = str;
                title = "Message";
            }
        }
        JOptionPane.showMessageDialog(null, Message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str, String title) {
        String msg;
        if (str.equals("sure")) {
            msg = "Are you sure you want to do this ?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }
    public static boolean isTableEmpty(JTable table) {
        // Tablonun satır ve sütun sayısını al
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();

        // Tablo satır ve sütunlarını döngüye alarak kontrol et
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                // Her hücrenin değerini kontrol et
                Object value = table.getValueAt(row, col);
                if (value != null && !value.toString().isEmpty()) {
                    // Eğer herhangi bir hücre doluysa, tablo dolu kabul edilir
                    return false;
                }
            }
        }
        // Eğer hiçbir hücre dolu değilse, tablo boş kabul edilir
        return true;
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static int getLocationPoint(String type, Dimension size) {
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static String firstWordUpper(String str){
        return str.substring(0,1).toUpperCase()+str.substring(1);
    }
}
