package com.inshining.poke.service;

import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.dto.follow.*;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.UserRepository;
import com.inshining.poke.domain.service.FollowService;
import com.inshining.poke.domain.service.UserService;
import org.junit.jupiter.api.Assertions;
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
    User user3;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void setup(){
        // given
        SignUpRequest request = new SignUpRequest("abc", "1234", "myname");
        SignUpRequest request2 = new SignUpRequest("abc2", "1234", "myname2");
        SignUpRequest request3 = new SignUpRequest("abc3", "1234", "myname3");

        userService.registerUser(request);
        userService.registerUser(request2);
        userService.registerUser(request3);

        user1 = userRepository.findByUsername("abc").orElseThrow(IllegalArgumentException::new);
        user2 = userRepository.findByUsername("abc2").orElseThrow(IllegalArgumentException::new);
        user3 = userRepository.findByUsername("abc3").orElseThrow(IllegalArgumentException::new);
    }

    @Test
    void Follow성공(){
        //given
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), user2.getId());

        //when
        FollowResponse followResponse =followService.followUser(followRequest);

        //then
        assertThat(followResponse.followerId()).isEqualTo(user1.getId());
        assertThat(followResponse.followingId()).isEqualTo(user2.getId());
    }

    @Test
    void follow_실패_본인(){
        //given
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), user1.getId());

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            FollowResponse response = followService.followUser(followRequest);
        });
    }

    @Test
    void follow_실패_잘못된_현유저(){
        //given
        FollowRequest followRequest = new FollowRequest("empty", user1.getId());

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            FollowResponse response = followService.followUser(followRequest);
        });
    }

    @Test
    void follow_실패_존재하지_않는_친구(){
        //given
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), 9999);

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            FollowResponse response = followService.followUser(followRequest);
        });
    }

    @Test
    void follow_성공_following_follower_친구추가(){
        //given
        // user1 -> user2 follow, user1 -> user3 follow
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), user2.getId());
        FollowRequest followRequest2 = new FollowRequest(user1.getUsername(), user3.getId());

        followService.followUser(followRequest);
        followService.followUser(followRequest2);

        MyFriendRequest request = new MyFriendRequest(user1.getUsername());


        //when
        MyFriendResponse response =followService.getMyFriends(request);

        //then
        assertThat(response.friendNames().size()).isEqualTo(2);
    }

    @Test
    void follow_성공_following_follower_중복_친구추가(){
        //given
        // user1 -> user2 follow, user1 -> user3 follow
        FollowRequest followRequest = new FollowRequest(user1.getUsername(), user2.getId());
        FollowRequest followRequest2 = new FollowRequest(user1.getUsername(), user3.getId());
        FollowRequest followRequest3 = new FollowRequest(user2.getUsername(), user1.getId());

        followService.followUser(followRequest);
        followService.followUser(followRequest2);
        followService.followUser(followRequest3);

        MyFriendRequest request = new MyFriendRequest(user1.getUsername());


        //when
        MyFriendResponse response =followService.getMyFriends(request);

        //then
        assertThat(response.friendNames().size()).isEqualTo(2);
    }
}
