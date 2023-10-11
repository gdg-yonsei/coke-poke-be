package com.inshining.poke.service;

import com.inshining.poke.domain.dto.SignInRequest;
import com.inshining.poke.domain.dto.SignInResponse;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.UserRepository;
import com.inshining.poke.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    UserServiceTest(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void clear(){
        userRepository.deleteAllInBatch();
    }

    @Test
    void login(){
        //given
        userRepository.save(User.builder()
                .username("aa")
                .password("1234")
                .name("inyeob")
                .build()
        );

        //when
        SignInResponse response = userService.signIn(new SignInRequest("aa", "11"));

        assertThat(response.name()).isEqualTo("inyeob");
    }
}
