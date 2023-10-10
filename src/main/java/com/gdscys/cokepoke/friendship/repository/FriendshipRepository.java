package com.gdscys.cokepoke.friendship.repository;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByRequestMember(Member requestMember, Pageable pageable);
    Page<Friendship> findAllByRecipientMember(Member recipientMember, Pageable pageable);
    Page<Friendship> findAllByRequestMemberOrRecipientMember(Member member, Pageable pageable);
    Optional<Friendship> findByRequestMemberAndRecipientMember(Member requestMember, Member recipientMember);
    Optional<Friendship> findByRequestMemberOrRecipientMemberAndAccepted(Member requestMember, Member recipientMember, boolean accepted);

}
