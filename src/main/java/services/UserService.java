package services;
import com.spring.universidad.cryptop2p.modelo.entidades.User;
import com.spring.universidad.cryptop2p.modelo.entidades.dto.UserRegisterDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserService {

    public User registerUser(UserRegisterDto userRegisterDto){
            User user = new User(userRegisterDto.getName(),
                    userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                    userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                    userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());

            return user;
        }
}
