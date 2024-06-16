package ru.nsu.dmustakaev;

import org.apache.commons.codec.digest.DigestUtils;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = DigestUtils.md5Hex(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
