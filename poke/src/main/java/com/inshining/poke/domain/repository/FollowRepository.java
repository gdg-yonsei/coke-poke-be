package com.inshining.poke.domain.repository;

import com.inshining.poke.domain.entity.follow.Follow;
import com.inshining.poke.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowerUser(User followerUser);
    List<Follow> findAllByFollowingUser(User followerUser);
}
