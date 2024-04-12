package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UserManagementView extends Layout{

    private JPanel container;
    private JTabbedPane tabbedPane_table_user;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_add;
    private JLabel lbl_admin_panel;
    private JComboBox cmbx_user_filter;
    private JLabel lbl_welcome;
    private JTable tbl_users;
    private JButton btn_logout;
    private JPanel pnl_admin;
    private JPanel pnl_welcome;
    private JPanel pnl_filter;
    private JPanel pnl_table_user;
    private JScrollPane scrl_table;
    private User user;
    private DefaultTableModel tmdl_users = new DefaultTableModel();
    private UserManager userManager;

    public UserManagementView(User user) {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Welcome :  " + Helper.firstWordUpper(this.user.getName()));

        // User List
        loadUsersTable(null);
        loadUsersFilter1();

        // LogOut
        logout();

    }

    private void loadUsersTable(ArrayList<Object[]> userList) {
        Object[] col_user_list = {"ID", "Name", "Password", "Role"};
        if (userList == null){
            userList = this.userManager.getForTable(col_user_list.length, this.userManager.findAll());
        }
        createTable(this.tmdl_users, this.tbl_users, col_user_list, userList);
    }


    public void loadUsersFilter1(){
        this.cmbx_user_filter.addItem("ALL USERS");
        this.cmbx_user_filter.addItem("ADMIN");
        this.cmbx_user_filter.addItem("EMPLOYEE");
        this.cmbx_user_filter.setSelectedItem("ALL USERS");
    }

    public void logout(){
        btn_logout.addActionListener(e -> {
            dispose();
            LoginView loginView = new LoginView();
        });
    }


}
