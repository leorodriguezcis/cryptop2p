package com.spring.universidad.cryptop2p.model.dto;


public class UserRegisterDTO {

    private String name;
    private String lastName;
    private String email;
    private String address;
    private String password;
    private String cvu;
    private String addrWallet;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getCvu() {
        return cvu;
    }

    public String getAddrWallet() {
        return addrWallet;
    }

    public UserRegisterDTO(String name, String lastName, String email, String address, String password
            , String cvu, String addrWallet){

        this.name =name;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cvu = cvu;
        this.addrWallet = addrWallet;
    }
    public UserRegisterDTO(){}

}
