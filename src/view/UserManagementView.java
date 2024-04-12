package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JPopupMenu user_menu;
    private Object[] col_model;
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

        // User Managemenet
        loadUsersTable();
        //loadUserComponent();
        this.tbl_users.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_users.rowAtPoint(e.getPoint());
                tbl_users.setRowSelectionInterval(selectedRow,selectedRow );
            }
        });
        this.user_menu = new JPopupMenu();
        this.user_menu.add("Add").addActionListener(e -> {
            UserView userView = new UserView(null);
        });
        this.user_menu.add("Update").addActionListener(e -> {
            int selectedUserId = Integer.parseInt(tbl_users.getValueAt(tbl_users.getSelectedRow(), 0).toString());
            UserView userView = new UserView(this.userManager.getById(selectedUserId ));
        });
        this.user_menu.add("Delete").addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to do this ?", "Delete", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                // Silme işlemini burada gerçekleştir
                // Örneğin: deleteItem();
            }
        });

        this.tbl_users.setComponentPopupMenu(user_menu);

        // LogOut
        logout();

    }

    private void loadUsersTable() {
        Object[] col_user_list = {"ID", "Name", "Password", "Role"};
        ArrayList<Object[]> userList = this.userManager.getForTable(col_user_list.length);
        this.createTable(this.tmdl_users, this.tbl_users, col_user_list, userList);
    }


    public void loadUsersFilter(){
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
