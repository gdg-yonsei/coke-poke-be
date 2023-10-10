package com.gdscys.cokepoke.friendship.repository;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findAllByRequestMember(Long requestMember, Pageable pageable);
    Page<Friendship> findAllByRecipientMember(Long recipientMember, Pageable pageable);
    Optional<Friendship> findByRequestMemberAndRecipientMember(Long requestMember, Long recipientMember);
    Optional<Friendship> findByRequestMemberOrRecipientMemberAndAccepted(Long requestMember, Long recipientMember, boolean accepted);

}
