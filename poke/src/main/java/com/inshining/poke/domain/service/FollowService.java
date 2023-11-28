package com.inshining.poke.domain.service;

import com.inshining.poke.domain.dto.follow.*;
import com.inshining.poke.domain.entity.follow.Follow;
import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.FollowRepository;
import com.inshining.poke.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public FollowResponse followUser(FollowRequest request) throws IllegalArgumentException{
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

        if (followerUser.getId().equals(followingUser.getId())){
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
        Set<String> followingSet = followRepository.findAllByFollowerUser(myUser).stream().map(follow -> follow.getFollowingUser().getName()).collect(Collectors.toSet());
        Set<String> followerSet = followRepository.findAllByFollowingUser(myUser).stream().map(follow -> follow.getFollowerUser().getName()).collect(Collectors.toSet());
        Set<String> mergedNameSet = new HashSet<>(followerSet);
        mergedNameSet.addAll(followingSet);

        List<FriendName> friendNames = mergedNameSet.stream().map(s -> FriendName.of(s)).collect(Collectors.toList());

        return MyFriendResponse.from(myUser, friendNames);
    }

}
