package view;

import business.UserManager;
import core.Helper;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        LoginGUI loginGUI = new LoginGUI();
    }
}