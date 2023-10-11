package com.inshining.poke;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:persistence-sqlite.properties")
@SpringBootTest
class PokeApplicationTests {

	@Test
	void contextLoads() {
	}

}
