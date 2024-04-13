import business.HotelManager;
import business.UserManager;
import core.Helper;
import view.EmployeeGUI;
import view.HotelAddUpdateGUI;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        //LoginView loginView = new LoginView();
        UserManager userManager = new UserManager();
        //UserManagementView userManagementView = new UserManagementView(userManager.findByLogin("ahmed","2024"));
        EmployeeGUI adminView = new EmployeeGUI(userManager.findByLogin("ahmed","2025"));
        HotelAddUpdateGUI hotelAddUpdateGUI = new HotelAddUpdateGUI(userManager.findByLogin("ahmed","2024"));
    }
}