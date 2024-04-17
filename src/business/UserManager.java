package business;

import core.Helper;
import dao.UserDao;
import entity.User;

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
    public ArrayList<User>findByAdmin(){
        return this.userDao.findByAdmin();
    }
    public ArrayList<User>findByEmployee(){
        return this.userDao.findByEmployee();
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

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg(id + " ID registered user not found");
            return false;
        }

        return this.userDao.delete(id);
    }

    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> userRowList = new ArrayList<>();
        for (User obj : this.findAll()) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getNameSurname();
            rowObject[i++] = obj.getUser();
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
