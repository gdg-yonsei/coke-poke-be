package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.member.domain.Member;

import java.util.List;

public interface IFriendshipService {

    void createFriendship(Member member, String recipientUsername);

    Friendship findFriendshipByMembers(Member member, String username2);

    List<Friendship> findFriendshipsByMember(Member member, int page);
}
