package com.inshining.poke.domain.service;

import com.inshining.poke.domain.dto.follow.FollowRequest;
import com.inshining.poke.domain.dto.follow.FollowResponse;
import com.inshining.poke.domain.dto.follow.MyFriendRequest;
import com.inshining.poke.domain.dto.follow.MyFriendResponse;
import com.inshining.poke.domain.entity.follow.Follow;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.FollowRepository;
import com.inshining.poke.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    private static Follow makeFollow(User follower, User followingUser){
        return Follow.builder()
                .follower(follower)
                .following(followingUser)
                .build();
    }

    public FollowResponse followUser(FollowRequest request){
        String followerUserName = request.followerName();
        Long followingUserId = request.followingId();
        boolean isFollowerExists = userRepository.existsByUsername(followerUserName);

        if (!isFollowerExists){
            throw new IllegalArgumentException("현재 존재 하지 않는 유저입니다.");
        }

        boolean isFollowingExists = userRepository.existsById(request.followingId());
        if (!isFollowingExists){
            throw new IllegalArgumentException("추가하려는 사용자는 존재하지 않습니다.");
        }
        User followerUser = userRepository.findByUsername(followerUserName).orElseThrow(IllegalArgumentException::new);
        User followingUser = userRepository.findById(followingUserId).orElseThrow(IllegalArgumentException::new);

        if (followerUser.equals(followingUser)){
            throw new IllegalArgumentException("자기 자신을 친구 추가할 수 없습니다.");
        }

        Follow follow = followRepository.save(makeFollow(followerUser, followingUser));
        return FollowResponse.from(follow);
    }

    public MyFriendResponse getMyFriends(MyFriendRequest request){
        String myUserName = request.myUsername();
        boolean isMyIdExists = userRepository.existsByUsername(myUserName);
        if (!isMyIdExists){
            throw new IllegalArgumentException("현재 사용자는 존재하지 않습니다.");
        }
        User myUser = userRepository.findByUsername(myUserName).orElseThrow(IllegalArgumentException::new);
        List<User> myFollowings = followRepository.findFollowingUsersByFollowerUser(myUser);
        return new MyFriendResponse(myUser, myFollowings);
    }

}
