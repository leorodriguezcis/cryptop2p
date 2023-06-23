package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import com.spring.universidad.cryptop2p.model.dto.UserLoginDTO;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import com.spring.universidad.cryptop2p.services.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends GenericService<User, UserRepository> implements UserDAO {

    private static final  String MSG_SUCCESS = "SUCCESS";
    private static final  String DATOS = "datos";
    private static final  String MESSAGE = "message";
    @Autowired
    public UserService(UserRepository repo) {
        super(repo);
    }

    @Autowired
    private JWTUtil jwtUtil;

    @Transactional
    public User registerUser(UserRegisterDTO userRegisterDto){

        User user = new User(userRegisterDto.getName(),
                userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
        repo.save(user);
        return user;
    }

    @Override
    public Map<String, Object> logIn(UserLoginDTO user) {
        Map<String, Object> message = new HashMap<>();
        Optional<User> userByName = repo.findByName(user.getUserName());
        System.out.println(userByName);
        if(userByName.isPresent()){
            if(userByName.get().getPassword().equals(user.getPassWord())){
                String token = jwtUtil.generateToken(user.getUserName());
                System.out.println(token);
                message.put(MSG_SUCCESS, Boolean.TRUE);
                message.put("token", token);
                return message;
            }
        }
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, String.format("nombre o contraseña incorrectos"));
        return message;
    }

}
