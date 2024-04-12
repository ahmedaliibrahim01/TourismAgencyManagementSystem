import business.UserManager;
import core.Helper;
import view.AdminView;
import view.LoginView;
import view.UserManagementView;
import view.UserView;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        //LoginView loginView = new LoginView();
        UserManager userManager = new UserManager();
        UserManagementView userManagementView = new UserManagementView(userManager.findByLogin("ahmed","2024"));
        //UserView userView = new UserView(userManager.findByLogin("ahmed","2024"));
        //AdminView adminView = new AdminView(userManager.findByLogin("ahmed","2025"));
    }
}