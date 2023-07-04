package com.spring.universidad.cryptop2p.model.dto;

public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO(String email, String pw) {
        this.email = email;
        this.password = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
