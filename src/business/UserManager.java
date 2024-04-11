package business;

import dao.UserDao;
import entity.User;

public class UserManager {
    private UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findUser(String username, String password) {
        return this.userDao.findByLogin(username, password);
    }
}
