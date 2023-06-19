package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends GenericService<User, UserRepository> implements UserDAO {
    @Autowired
    public UserService(UserRepository repo) {
        super(repo);
    }

    @Transactional
    public User registerUser(UserRegisterDTO userRegisterDto){

        User user = new User(userRegisterDto.getName(),
                userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
        repo.save(user);
        return user;
    }

}
