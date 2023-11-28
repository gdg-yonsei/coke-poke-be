package gdsc.cokepoke.controller;

import gdsc.cokepoke.domain.dto.AuthResponse;
import gdsc.cokepoke.domain.dto.LoginRequest;
import gdsc.cokepoke.domain.dto.SignupRequest;
import gdsc.cokepoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;


    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignupRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }


}
