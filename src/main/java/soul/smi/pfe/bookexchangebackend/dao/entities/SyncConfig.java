package soul.smi.pfe.bookexchangebackend.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import soul.smi.pfe.bookexchangebackend.service.UserService;
@Data
@AllArgsConstructor
@Configuration
@EnableScheduling
public class SyncConfig {

    private UserService userService;

    @Scheduled(fixedDelay = 30*1000) // Run every half minute
    public void syncUsers() {
        userService.syncUsers();
    }
}
