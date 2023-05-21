package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDTO;

public interface UserDAO extends GenericDAO<User>{
     User registerUser(UserRegisterDTO userRegisterDto);
}
