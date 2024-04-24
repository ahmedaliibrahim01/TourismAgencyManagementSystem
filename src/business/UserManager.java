package business;

import core.Helper;
import dao.UserDAO;
import entity.User;

import java.util.ArrayList;

/**
 * Manages user-related business logic.
 */
public class UserManager {
    private UserDAO userDao;

    /**
     * Constructs a UserManager object.
     */
    public UserManager() {
        this.userDao = new UserDAO();
    }

    /**
     * Finds a user by login credentials (username and password).
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user found, or null if not found.
     */
    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username, password);
    }

    /**
     * Retrieves all users.
     * @return A list of all users.
     */
    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }

    /**
     * Retrieves users with the role of admin.
     * @return A list of admin users.
     */
    public ArrayList<User> findByAdmin() {
        return this.userDao.findByAdmin();
    }

    /**
     * Retrieves users with the role of employee.
     * @return A list of employee users.
     */
    public ArrayList<User> findByEmployee() {
        return this.userDao.findByEmployee();
    }

    /**
     * Saves a user.
     * @param user The user to save.
     * @return True if the user is saved successfully, otherwise false.
     */
    public boolean save(User user) {
        if (user.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.userDao.save(user);
    }

    /**
     * Updates a user.
     * @param user The user to update.
     * @return True if the user is updated successfully, otherwise false.
     */
    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMsg("notFound");
        }
        return this.userDao.update(user);
    }

    /**
     * Deletes a user by ID.
     * @param id The ID of the user to delete.
     * @return True if the user is deleted successfully, otherwise false.
     */
    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID registered user not found");
            return false;
        }
        return this.userDao.delete(id);
    }

    /**
     * Retrieves user data for a table.
     * @param size The size of the data to retrieve.
     * @return A list of objects representing user data for the table.
     */
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> userRowList = new ArrayList<>();
        for (User obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getFullName();
            rowObject[i++] = obj.getUserName();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userRowList.add(rowObject);
        }
        return userRowList;
    }

    /**
     * Retrieves a user by ID.
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID, or null if not found.
     */
    public User getById(int id) {
        return this.userDao.getById(id);
    }
}
