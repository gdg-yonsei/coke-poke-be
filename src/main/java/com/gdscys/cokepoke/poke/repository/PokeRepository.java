package com.gdscys.cokepoke.poke.repository;

import com.gdscys.cokepoke.friendship.domain.Friendship;
import com.gdscys.cokepoke.poke.domain.Poke;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokeRepository extends JpaRepository<Poke, Long> {
    Page<Poke> findAllByFriendship(Friendship friendship, Pageable pageable);

}
