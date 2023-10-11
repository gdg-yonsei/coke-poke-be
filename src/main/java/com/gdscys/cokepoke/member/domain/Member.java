package com.gdscys.cokepoke.member.domain;

import com.gdscys.cokepoke.validation.declaration.ValidEmail;
import javax.persistence.*;
import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "Member")
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(indexes = {
        @Index(name = "idx_member_email", columnList = "email", unique = true)
})
public class Member implements UserDetails {

    @Id @Column(name = "id", updatable = false, nullable = false, unique = true)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(name = "email", unique = true, nullable = false, columnDefinition = "varchar(40)")
    @ValidEmail
    private String email;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(20)")
    private String username;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired = false;


    protected Member() {}

    public Member(String email, String username, String passwordHash, Set<String> roles) {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }

    public void updatePassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
