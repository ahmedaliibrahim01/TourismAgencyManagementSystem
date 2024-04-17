package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM public.user ORDER BY user_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public boolean save(User user) {
        String query = "INSERT INTO public.user (user_name_surname, user_user, user_password, user_role) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1,user.getNameSurname());
            pr.setString(2, user.getUser());
            pr.setString(3, user.getPassword());
            pr.setString(4, user.getRole().toString());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean update(User user) {
        String query = "UPDATE public.user SET user_name_surname = ?,user_user = ?, user_password = ?, user_role = ? WHERE user_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, user.getNameSurname());
            pr.setString(2, user.getUser());
            pr.setString(3, user.getPassword());
            pr.setString(4, user.getRole().toString());
            pr.setInt(5, user.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM public.user WHERE user_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_user = ? AND user_password = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public User getById(int id) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_id = ? ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public ArrayList<User> findByAdmin(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.user WHERE user_role = 'ADMIN' ORDER BY user_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public ArrayList<User> findByEmployee(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.user WHERE user_role = 'EMPLOYEE' ORDER BY user_id ASC";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()) {
                userList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setNameSurname(rs.getString("user_name_surname"));
        obj.setUser(rs.getString("user_user"));
        obj.setPassword(rs.getString("user_password"));
        obj.setRole(User.Role.valueOf(rs.getString("user_role")));
        return obj;
    }
}
