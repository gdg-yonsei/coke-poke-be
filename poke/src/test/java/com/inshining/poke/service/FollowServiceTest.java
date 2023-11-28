package com.inshining.poke.service;

import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.follow.FollowRequest;
import com.inshining.poke.domain.dto.follow.FollowResponse;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.UserRepository;
import com.inshining.poke.domain.service.FollowService;
import com.inshining.poke.domain.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FollowServiceTest {

    User user1;
    User user2;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void setup(){
        SignUpRequest request = new SignUpRequest("abc", "1234", "myname");
        SignUpRequest request2 = new SignUpRequest("abc2", "1234", "myname2");
        userService.registerUser(request);
        userService.registerUser(request2);

        user1 = userRepository.findByUsername("abc").orElseThrow(IllegalArgumentException::new);
        user2 = userRepository.findByUsername("abc2").orElseThrow(IllegalArgumentException::new);
    }

    @Test
    void Follow성공(){
        //given
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), user2.getId());

        //when
        FollowResponse followResponse =followService.followUser(followRequest);
        assertThat(followResponse.followerId()).isEqualTo(user1.getId());
        assertThat(followResponse.followingId()).isEqualTo(user2.getId());
    }

}
