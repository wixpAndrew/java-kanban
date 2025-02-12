package server;

import org.junit.jupiter.api.Test;

public class User {

    private String name;
    private int id;

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId () {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", name = " + name + '\'' +
                "}";
    }

}

