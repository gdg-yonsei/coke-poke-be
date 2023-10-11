package com.gdscys.cokepoke.member.repository;

import com.gdscys.cokepoke.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByMember_Email(String email);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
