package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.modelo.entities.User;
import com.spring.universidad.cryptop2p.modelo.entities.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.modelo.entities.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl extends GenericDAOImpl<User, UserRepository>  implements UserDAO {
    @Autowired
    public UserDaoImpl(UserRepository repo) {
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
    public List<UserRegisterDTO> getUsers() {
        List<UserRegisterDTO> users = new ArrayList<>();
        for (User user :repo.findAll()){
            UserRegisterDTO  userR = new UserRegisterDTO(user.getName(),
                    user.getLastname(),user.getEmail(),
                    user.getAddress(),user.getPassword(),
                    user.getCvu(),user.getWallet());
            users.add(userR);
        }

        return users ;
    }
    @Transactional(readOnly = true)
    public Optional<User> findUsersByName(String nombre) {
        return repo.findUsersByName(nombre);
    }

}
