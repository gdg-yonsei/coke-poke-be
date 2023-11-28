package com.inshining.poke.controller;

import com.inshining.poke.domain.dto.follow.*;
import com.inshining.poke.domain.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class FriendController {

    private final FollowService service;

    @PostMapping("/friend")
    public FollowResponse makeFriend(@RequestBody FollowingRequest request, Principal principal){
        FollowRequest followRequest = new FollowRequest(principal.getName(), request.followingId());
        return service.followUser(followRequest);
    }

    @GetMapping("/my-friends")
    public MyFriendResponse getMyFriends(Principal principal){
        MyFriendRequest request = new MyFriendRequest(principal.getName());
        return service.getMyFriends(request);
    }
}
