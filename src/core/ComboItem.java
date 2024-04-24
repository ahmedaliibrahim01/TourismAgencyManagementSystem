package core;

/**
 * Represents an item in a combo box, with a key and a value.
 */
public class ComboItem {
    private int key;
    private String value;

    /**
     * Constructs a ComboItem with the specified key and value.
     * @param key The key of the combo item.
     * @param value The value of the combo item.
     */
    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Constructs a ComboItem with only a value.
     * @param value The value of the combo item.
     */
    public ComboItem(String value) {
        this.value = value;
    }

    /**
     * Retrieves the key of the combo item.
     * @return The key of the combo item.
     */
    public int getKey() {
        return key;
    }

    /**
     * Sets the key of the combo item.
     * @param key The key to set.
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Retrieves the value of the combo item.
     * @return The value of the combo item.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the combo item.
     * @param value The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns a string representation of the combo item.
     * @return The string representation of the combo item (its value).
     */
    public String toString() {
        return this.value;
    }
}
