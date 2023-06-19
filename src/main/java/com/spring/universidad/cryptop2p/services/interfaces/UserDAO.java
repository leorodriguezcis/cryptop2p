package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;

public interface UserDAO extends GenericDAO<User>{
     User registerUser(UserRegisterDTO userRegisterDto);
}
