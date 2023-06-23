package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserLoginDTO;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;

import java.util.Map;

public interface UserDAO extends GenericDAO<User>{
     User registerUser(UserRegisterDTO userRegisterDto);

     Map<String, Object> logIn(UserLoginDTO user);
}
