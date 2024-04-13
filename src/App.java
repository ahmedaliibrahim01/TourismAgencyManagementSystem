import business.UserManager;
import core.Helper;
import view.EmployeeView;
import view.UserManagementView;
import view.UserView;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        //LoginView loginView = new LoginView();
        UserManager userManager = new UserManager();
        //UserManagementView userManagementView = new UserManagementView(userManager.findByLogin("ahmed","2024"));
        EmployeeView adminView = new EmployeeView(userManager.findByLogin("ahmed","2025"));
    }
}