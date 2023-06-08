package soul.smi.pfe.bookservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import soul.smi.pfe.bookservice.service.InitBooks;

@SpringBootApplication
@EnableFeignClients
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(InitBooks initBooks){
		return args -> {
			initBooks.initBooks();
		};
	}
}
