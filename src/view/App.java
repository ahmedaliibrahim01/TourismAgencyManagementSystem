package view;

import business.UserManager;
import core.Helper;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        LoginGUI loginGUI = new LoginGUI();
        UserManager userManager = new UserManager();
        //UserManagementGUI userManagementGUI = new UserManagementGUI(userManager.findByLogin("ahmed","2024"));
        //EmployeeGUI adminView = new EmployeeGUI(userManager.findByLogin("ahmed","2025"));
        //HotelAddUpdateGUI hotelAddUpdateGUI = new HotelAddUpdateGUI(userManager.findByLogin("ahmed","2024"));
        //AddGUI add = new AddGUI();
    }
}