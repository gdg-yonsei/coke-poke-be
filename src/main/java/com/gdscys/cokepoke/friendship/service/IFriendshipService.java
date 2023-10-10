package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.member.domain.Member;

import java.util.List;

public interface IFriendshipService {

    void createFriendship(String username, String recipientUsername);
    Friendship getFriendshipById(Long friendshipId);

    Friendship getFriendshipByMembers(String username, String username2);

    List<Friendship> getFriendshipsByMember(String username, int page);
}
