package com.inshining.poke.domain.entity.user;

import com.inshining.poke.domain.dto.SignUpRequest;
import com.inshining.poke.domain.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Builder
    public User(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
    }



    public Collection<? extends GrantedAuthority> getAuthorities(){
        return new ArrayList<>();
    }
}
