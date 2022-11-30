package magicTheBuilder.userservice.controller;


import magicTheBuilder.userservice.dto.LoginRequestDto;
import magicTheBuilder.userservice.dto.RegisterRequestDto;
import magicTheBuilder.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto dto) {
        return userService.login(dto);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDto dto) {
        userService.register(dto);
    }
}
