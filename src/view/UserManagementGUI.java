package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class UserManagementGUI extends Layout{
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
    private JLabel lbl_filter;
    private JTextField txtf_selected_id;
    private JLabel lbl_ID;
    private JPopupMenu user_menu;
    private Object[] col_model;
    private User user;
    private DefaultTableModel tmdl_users = new DefaultTableModel();
    private UserManager userManager;

    public UserManagementGUI(User user) {
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(1000, 500);
        container.setPreferredSize(new Dimension(1000,500));
        this.user = user;
        if (this.user == null) {
            dispose();
        }
        this.lbl_welcome.setText("Welcome :  " + Helper.firstWordUpper(this.user.getName()));

        // User Management
        loadUsersTable();
        loadUserComponent();
        loadUserComponentButtons();
        this.tbl_users.setComponentPopupMenu(user_menu);
        logout();
    }

    private void loadUserComponentButtons() {
        btn_update.addActionListener(e -> {
            int selectedUserId = this.getTableSelectedRow(tbl_users,0);
            if (selectedUserId != -1) {
                UserGUI userView = new UserGUI(this.userManager.getById(selectedUserId));
                userView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadUsersTable();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(UserManagementGUI.this, "Please select a row.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            }

        });

        btn_delete.addActionListener(e -> {
            int selectedUserId = this.getTableSelectedRow(tbl_users,0);
            if (selectedUserId != -1){
                if (Helper.confirm("sure","Delete")){
                    if (this.userManager.delete(selectedUserId)){
                        Helper.showMsg("done");
                        loadUsersTable();
                    }else {
                        Helper.showMsg("error");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(UserManagementGUI.this, "Please select a row.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        btn_add.addActionListener(e -> {
            UserGUI userView = new UserGUI(null);
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUsersTable();
                }
            });
        });
    }

    private void loadUserComponent() {
        this.tbl_users.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_users.rowAtPoint(e.getPoint());
                tbl_users.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.user_menu = new JPopupMenu();
        this.user_menu.add("Add").addActionListener(e -> {
            UserGUI userView = new UserGUI(null);
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUsersTable();
                }
            });
        });

        this.user_menu.add("Update").addActionListener(e -> {
            int selectedUserId = getTableSelectedRow(tbl_users, 0);
            if (selectedUserId != -1) {
                UserGUI userView = new UserGUI(userManager.getById(selectedUserId));
                userView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadUsersTable();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(UserManagementGUI.this, "Please select a row.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            }
        });

        this.user_menu.add("Delete").addActionListener(e -> {
            int selectedUserId = this.getTableSelectedRow(tbl_users,0);
            if (selectedUserId != -1){
                if (Helper.confirm("sure","Delete")){
                    if (this.userManager.delete(selectedUserId)){
                        Helper.showMsg("done");
                        loadUsersTable();
                    }else {
                        Helper.showMsg("error");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(UserManagementGUI.this, "Please select a row.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            }

        });
    }

    private void loadUsersTable() {
        Object[] col_user_list = {"ID", "Name", "Password", "Role"};
        ArrayList<Object[]> userList = this.userManager.getForTable(col_user_list.length);
        this.createTable(this.tmdl_users, this.tbl_users, col_user_list, userList);
    }

    public void logout(){
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginView = new LoginGUI();
        });
    }


}
