package magicTheBuilder.userservice.service;



import magicTheBuilder.userservice.dto.LoginRequestDto;
import magicTheBuilder.userservice.dto.RegisterRequestDto;
import magicTheBuilder.userservice.entity.User;
import magicTheBuilder.userservice.exception.customExceptions.DuplicatedEmailException;
import magicTheBuilder.userservice.exception.customExceptions.DuplicatedUsernameException;
import magicTheBuilder.userservice.exception.customExceptions.InvalidPasswordException;
import magicTheBuilder.userservice.exception.customExceptions.UnrecognizedEmailException;
import magicTheBuilder.userservice.jwt.JwtUtil;
import magicTheBuilder.userservice.rabbit.Sender;
import magicTheBuilder.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String register(RegisterRequestDto dto)
    {
        User user = new User();
        validateUsernameNotTaken(dto.getUsername());
        user.setUsername(dto.getUsername());
        if(dto.getPassword().equals(dto.getPassword_repeat())) {
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        validateEmailNotTaken(dto.getEmail());
        user.setEmail(dto.getEmail());
        user.setAllowNotifications(dto.isAllowNotifications());
        user.setAllowDataProcessing(dto.isAllowDataProcessing());
        user = saveUser(user);
        sender.sendMsg(user.getId());
        return user.getId().toString();
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

    private void validateUsernameNotTaken(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            throw new DuplicatedUsernameException(username);
        }
    }

    private void validateEmailNotTaken(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            throw new DuplicatedEmailException(email);
        }
    }
}
