package com.inshining.poke.domain.service;

import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.SignUpResponse;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public SignUpResponse registerUser(SignUpRequest request) {
        System.out.println(request);
//        User user = User.from(request, encoder);
        User user = userRepository.save(User.from(request, encoder));
        try {
            userRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        return SignUpResponse.from(user);
    }
}
