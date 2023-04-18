package com.spring.universidad.cryptop2p.services;
import com.spring.universidad.cryptop2p.modelo.entidades.User;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.UserRegisterDto;
import com.spring.universidad.cryptop2p.modelo.entidades.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User registerUser(UserRegisterDto userRegisterDto){

            User user = new User(userRegisterDto.getName(),
                    userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                    userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                    userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
                    userRepository.save(user);
            return user;
        }
    public List<UserRegisterDto> getUsers() {
        List<UserRegisterDto> users = new ArrayList<>();
        for (User user :userRepository.findAll()){
            UserRegisterDto  userR = new UserRegisterDto(user.getName(),
                    user.getLastname(),user.getEmail(),
                    user.getAddress(),user.getPassword(),
                    user.getCvu(),user.getWallet());
            users.add(userR);
        }

        return users ;
    }
        public  UserService (UserRepository userRepository){
        this.userRepository = userRepository;
        }
}
