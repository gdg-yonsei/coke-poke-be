package com.inshining.poke.repository;

import com.inshining.poke.domain.entity.user.User;
import com.inshining.poke.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    void save(){
        User user = User.builder()
                .username("aa")
                .password("1234")
                .name("inyeob")
                .build();

        User madeUser = userRepository.save(user);

        assertThat(madeUser.getId()).isEqualTo(1);
    }

    @Test
    void findbyUsername(){
        userRepository.save(
                User.builder()
                        .username("aa")
                        .password("1234")
                        .name("inyeob")
                        .build()
        );


        User user = userRepository.findByUsername("aa").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("aa");
    }
}
