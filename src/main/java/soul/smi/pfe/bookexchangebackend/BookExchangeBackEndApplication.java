package soul.smi.pfe.bookexchangebackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import soul.smi.pfe.bookexchangebackend.service.BookExchangeInit;

@SpringBootApplication
public class BookExchangeBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookExchangeBackEndApplication.class, args);
    }
@Bean
public CommandLineRunner hello(BookExchangeInit bookExchangeInit){
        return args -> {
            bookExchangeInit.initBookImages();
            bookExchangeInit.initUserImages();
            bookExchangeInit.initRegisteredUseres();
            bookExchangeInit.initBooks();
            bookExchangeInit.initComment();
        };
}

}
