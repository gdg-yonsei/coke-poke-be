package com.cokepoke.app;

import com.cokepoke.app.user.SiteUser;
import com.cokepoke.app.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	/*
	* id : testid , username : testuser, email : test@email.com , password : 1234
	 */
	void testJpa() {
		List<SiteUser> all = this.userRepository.findAll();
		Optional<SiteUser> userOptional = this.userRepository.findByUserId("testid");
		SiteUser firstUser = userOptional.get();
		Assertions.assertThat("testuser").isEqualTo(firstUser.getUserName());
		Assertions.assertThat("test@email.com").isEqualTo(firstUser.getEmail());
	}
}
