package soul.smi.pfe.ChatComservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import soul.smi.pfe.ChatComservice.service.InitComments;

@SpringBootApplication
@EnableFeignClients
public class ChatComServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatComServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(InitComments initComments){
		return args -> {
		initComments.initComments();
		};
	}
}
