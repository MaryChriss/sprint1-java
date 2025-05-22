package fiap.com.br.future_stack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FutureStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FutureStackApplication.class, args);
	}

}
