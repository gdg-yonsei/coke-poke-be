package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.member.domain.Member;

import java.util.List;

public interface IFriendshipService {

    void createFriendship(String username, String recipientUsername);

    Friendship findFriendshipByMembers(String username, String username2);

    List<Friendship> findFriendshipsByMember(String username, int page);
}
