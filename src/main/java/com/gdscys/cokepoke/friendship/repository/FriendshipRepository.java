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

    Page<Friendship> findAllByFrom(Member from, Pageable pageable);
    Page<Friendship> findAllByTo(Member to, Pageable pageable);
    Optional<Friendship> findByFromAndTo(Member from, Member to);
    Optional<Friendship> findByFrom(Member from);
}
