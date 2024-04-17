package entity;

public class User {
    private int id;
    private String nameSurname;
    private String user;
    private String password;
    private Role role;

    public User() {
    }

    public User(int id, String nameSurname, String user, String password, Role role) {
        this.id = id;
        this.nameSurname = nameSurname;
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public enum Role{
        ADMIN,
        EMPLOYEE
    }

    public User(String nameSurname, String user, String password, Role role) {
        this.nameSurname = nameSurname;
        this.user = user;
        this.password = password;
        this.role = role;
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
                ", nameSurname='" + nameSurname + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}