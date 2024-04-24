package entity;

/**
 * Represents a user entity in the system.
 */
public class User {
    private int id;
    private String fullName;
    private String userName;
    private String password;
    private Role role;

    // Constructors and methods...
    public User() {
    }

    public User(int id, String nameSurname, String user, String password, Role role) {
        this.id = id;
        this.fullName = nameSurname;
        this.userName = user;
        this.password = password;
        this.role = role;
    }

    /**
     * Enumeration representing roles of users in the system.
     */
    public enum Role{
        ADMIN,
        EMPLOYEE
    }

    public User(String nameSurname, String user, String password, Role role) {
        this.fullName = nameSurname;
        this.userName = user;
        this.password = password;
        this.role = role;
    }

    public User(String user, String password) {
        this.userName = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nameSurname='" + fullName + '\'' +
                ", user='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}