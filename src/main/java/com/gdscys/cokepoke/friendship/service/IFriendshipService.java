package com.gdscys.cokepoke.friendship.service;

import com.gdscys.cokepoke.member.domain.Member;

public interface IFriendshipService {

    void createFriendship(Member requestMember, Member recipientMember);
    void deleteFriendship(Member requestMember, Member recipientMember);
}
