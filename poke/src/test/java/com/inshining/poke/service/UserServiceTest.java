package com.inshining.poke.service;

import com.inshining.poke.config.security.TokenProvider;
import com.inshining.poke.domain.dto.SignInRequest;
import com.inshining.poke.domain.dto.SignInResponse;
import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.SignUpResponse;
import com.inshining.poke.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void 회원가입_성공(){
        //given
        SignUpRequest request = new SignUpRequest("abc", "1234", "myname");

        //when
        SignUpResponse response = userService.registerUser(request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.username()).isEqualTo(request.username());
    }

    @Test
    void 중복회원가입(){
        //given
        SignUpRequest request = new SignUpRequest("abc", "1234", "myname");
        SignUpRequest request2 = new SignUpRequest("abc", "1234", "myname");

        //when
        SignUpResponse response = userService.registerUser(request);
        SignUpResponse response2 = userService.registerUser(request2);

    }

    @Test
    void login성공(){
        //given
        userService.registerUser(new SignUpRequest("aa", "11", "myname"));
        SignInRequest request = new SignInRequest("aa", "11");

        //when
        SignInResponse response = userService.signIn(request);

        assertThat(response.name()).isEqualTo("myname");
    }
}
