package com.spring.universidad.cryptop2p.services.interfaces;


import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserDAO extends GenericDAO<User>{
     User registerUser(UserRegisterDTO userRegisterDto);
     @Transactional(readOnly = true)
     Optional<User> findUsersByName(String nombre);
}
