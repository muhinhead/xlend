package com.xlend.util;

/**
 *
 * @author Nick Mukhin
 */
public class ComboItem {
    private int id;
    private String string;
    public ComboItem(int id, String string) {
        this.id = id;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String str) {
        string = str;
    }

    public String toString() {
        return string;
    }
}
