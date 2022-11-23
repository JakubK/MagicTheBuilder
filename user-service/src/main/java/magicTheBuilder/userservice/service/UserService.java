package magicTheBuilder.userservice.service;



import magicTheBuilder.userservice.dto.LoginRequestDto;
import magicTheBuilder.userservice.dto.RegisterRequestDto;
import magicTheBuilder.userservice.entity.User;
import magicTheBuilder.userservice.exception.customExceptions.*;
import magicTheBuilder.userservice.jwt.JwtUtil;
import magicTheBuilder.userservice.rabbit.Sender;
import magicTheBuilder.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Sender sender;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String login(LoginRequestDto dto)
    {
        User user = getUserByEmail(dto.getEmail());
        String encodedLoginPass = bCryptPasswordEncoder.encode(dto.getPassword());
        if(bCryptPasswordEncoder.matches(dto.getPassword(),encodedLoginPass))
        {
            return jwtUtil.createToken(user);
        }
        else{
            throw new InvalidPasswordException();
        }
    }
    public void register(RegisterRequestDto dto)
    {
        User user = new User();
        validateUsernameNotAlreadyUsed(dto.getUsername());
        user.setUsername(dto.getUsername());
        if(dto.getPassword().equals(dto.getPassword_repeat())) {
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        validateEmailFormat(dto.getEmail());
        validateEmailNotAlreadyUsed(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setAllowNotifications(dto.isAllowNotifications());
        user.setAllowDataProcessing(dto.isAllowDataProcessing());
        user = saveUser(user);
        sender.sendMsg(user.getId());
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    private User getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new UnrecognizedEmailException();
        }
    }

    private void validateUsernameNotAlreadyUsed(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            throw new DuplicatedUsernameException(username);
        }
    }

    private void validateEmailNotAlreadyUsed(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new DuplicatedEmailException(email);
        }
    }

    private void validateEmailFormat(String email){
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new InvalidEmailFormatException(email);
        }
    }
}
