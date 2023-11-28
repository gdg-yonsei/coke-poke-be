package gdsc.cokepoke.service;

import gdsc.cokepoke.domain.dto.SignupRequest;
import gdsc.cokepoke.domain.entity.User;
import gdsc.cokepoke.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

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

    private boolean usernameExists(String username) {
        return repository.existsByUsername(username);
    }

}
