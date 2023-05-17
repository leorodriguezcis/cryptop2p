package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDto;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDaoImpl extends GenericDAOImpl<User, UserRepository>  implements UserDAO {
    @Autowired
    public UserDaoImpl(UserRepository repo) {
        super(repo);
    }

    @Transactional
    public User registerUser(UserRegisterDto userRegisterDto){

        User user = new User(userRegisterDto.getName(),
                userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
        repo.save(user);
        return user;
    }
}
