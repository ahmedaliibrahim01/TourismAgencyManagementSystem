package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password) {
        return this.userDao.findByLogin(username, password);
    }

    public ArrayList<User> findAll() {
        return this.userDao.findAll();
    }
    public boolean save(User user) {
        if (user.getId() != 0) {
            Helper.showMsg("error");
        }
        return this.userDao.save(user);
    }

    public boolean update(User user) {
        if (this.getById(user.getId()) == null) {
            Helper.showMsg("notFound");
        }
        return this.userDao.update(user);
    }
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> userRowList = new ArrayList<>();
        for (User obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getPassword();
            rowObject[i++] = obj.getRole();
            userRowList.add(rowObject);
        }
        return userRowList;
    }

    public User getById(int id) {
        return this.userDao.getById(id);
    }
}
