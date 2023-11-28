package gdsc.cokepoke.service;

import gdsc.cokepoke.domain.dto.AuthResponse;
import gdsc.cokepoke.domain.dto.LoginRequest;
import gdsc.cokepoke.domain.dto.SignupRequest;
import gdsc.cokepoke.domain.entity.User;
import gdsc.cokepoke.repository.UserRepository;
import gdsc.cokepoke.security.JWTGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;


    public User createUser(SignupRequest request, PasswordEncoder encoder) {
        return User.builder()
                .username(request.username())
                .password(encoder.encode(request.password()))
                .name(request.name())
                .build();
    }

    @Transactional
    public ResponseEntity<String> registerUser(SignupRequest request) {
        boolean usernameDuplicate = usernameExists(request.username());
        if (usernameDuplicate) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User name already exists.");
        }

        User user = repository.save(createUser(request, passwordEncoder));
        repository.flush();

        String responseMessage = "User " + user.getUsername() + " successfully registered.";
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    private boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }

}
