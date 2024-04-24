package dao;

import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object (DAO) for User entity.
 */
public class UserDAO {
    private final Connection connection;

    /**
     * Constructor to initialize the UserDAO with a database connection.
     */
    public UserDAO() {
        this.connection = Db.getInstance();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return ArrayList of User objects containing all users.
     */
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

    /**
     * Saves a new user to the database.
     *
     * @param user The User object to be saved.
     * @return True if the user is successfully saved, false otherwise.
     */
    public boolean save(User user) {
        String query = "INSERT INTO public.user (user_name_surname, user_user, user_password, user_role) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, user.getFullName());
            pr.setString(2, user.getUserName());
            pr.setString(3, user.getPassword());
            pr.setString(4, user.getRole().toString());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user The User object with updated information.
     * @return True if the user is successfully updated, false otherwise.
     */
    public boolean update(User user) {
        String query = "UPDATE public.user SET user_name_surname = ?,user_user = ?, user_password = ?, user_role = ? WHERE user_id = ?";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setString(1, user.getFullName());
            pr.setString(2, user.getUserName());
            pr.setString(3, user.getPassword());
            pr.setString(4, user.getRole().toString());
            pr.setInt(5, user.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id The ID of the user to be deleted.
     * @return True if the user is successfully deleted, false otherwise.
     */
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

    /**
     * Finds a user in the database by their login credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if found, null otherwise.
     */
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

    /**
     * Finds a user in the database by their ID.
     *
     * @param id The ID of the user to be found.
     * @return The User object if found, null otherwise.
     */
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

    /**
     * Finds all users in the database with the role 'ADMIN'.
     *
     * @return ArrayList of User objects with the role 'ADMIN'.
     */
    public ArrayList<User> findByAdmin() {
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

    /**
     * Finds all users in the database with the role 'EMPLOYEE'.
     *
     * @return ArrayList of User objects with the role 'EMPLOYEE'.
     */
    public ArrayList<User> findByEmployee() {
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

    /**
     * Maps a ResultSet to a User object.
     *
     * @param rs The ResultSet containing user data.
     * @return User object mapped from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setFullName(rs.getString("user_name_surname"));
        obj.setUserName(rs.getString("user_user"));
        obj.setPassword(rs.getString("user_password"));
        obj.setRole(User.Role.valueOf(rs.getString("user_role")));
        return obj;
    }
}
