package entity;

public class PensionType {
    private int id;
    private String name;

    public PensionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PensionType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PensionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
