package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDto;

public interface UserDAO extends GenericDAO<User>{
     User registerUser(UserRegisterDto userRegisterDto);
}
