package com.spring.universidad.cryptop2p.services.implementation;

import com.spring.universidad.cryptop2p.model.config.JWTUtil;
import com.spring.universidad.cryptop2p.model.dto.UserLoginDTO;
import com.spring.universidad.cryptop2p.model.entities.User;
import com.spring.universidad.cryptop2p.model.dto.UserRegisterDTO;
import com.spring.universidad.cryptop2p.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService extends GenericService<User, UserRepository> {

    private static final  String MSG_SUCCESS = "SUCCESS";
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

    public Map<String, Object> logIn(UserLoginDTO user) {
        Map<String, Object> message = new HashMap<>();
        Optional<User> userByEmail = repo.findByEmail(user.getEmail());
        if(validateUser(userByEmail, user)){
            String token = jwtUtil.generateToken(user.getEmail());
            message.put(MSG_SUCCESS, Boolean.TRUE);
            message.put("token", token);
            return message;
            
        }
        message.put(MSG_SUCCESS, Boolean.FALSE);
        message.put(MESSAGE, "nombre o contraseña incorrectos");
        return message;
    }
    public boolean validateUser(Optional<User> userByEmail, UserLoginDTO user){
        return userByEmail.isPresent()&&userByEmail.get().getPassword().equals(user.getPassword());
    }
}
