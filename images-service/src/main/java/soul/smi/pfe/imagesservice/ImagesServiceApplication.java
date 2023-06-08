package soul.smi.pfe.imagesservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.service.InitImages;
import soul.smi.pfe.imagesservice.service.PicturesService;

@SpringBootApplication
public class ImagesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagesServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner start(InitImages initImages){
		return args -> {
			initImages.initImages();
		};
	}



}
