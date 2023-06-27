package com.spring.universidad.cryptop2p.model.dto;

public class UserLoginDTO {

    private String username;
    private String password;

    public UserLoginDTO(String user, String pw) {
        this.username = user;
        this.password = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
