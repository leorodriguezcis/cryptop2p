package services;
import com.spring.universidad.cryptop2p.modelo.entidades.User;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.UserRegisterDto;
import com.spring.universidad.cryptop2p.modelo.entidades.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



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
        public UserService (){
        }
        public  UserService (UserRepository userRepository){
        this.userRepository = userRepository;
        }
}
