package gdsc.cokepoke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CokePokeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CokePokeApplication.class, args);
	}

}
