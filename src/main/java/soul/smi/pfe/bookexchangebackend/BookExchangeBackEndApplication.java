package soul.smi.pfe.bookexchangebackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import soul.smi.pfe.bookexchangebackend.service.BookExchangeInit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class BookExchangeBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookExchangeBackEndApplication.class, args);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*");
            }
        };
    }

@Bean
public CommandLineRunner hello(BookExchangeInit bookExchangeInit){
        return args -> {
            Thread.sleep(10000);
            bookExchangeInit.initUsers();
            bookExchangeInit.initBooks();
            bookExchangeInit.initComments();
        };
}

}
