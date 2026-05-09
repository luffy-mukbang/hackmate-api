package yoon_hyojin.hackmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HackmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HackmateApplication.class, args);
	}

}
