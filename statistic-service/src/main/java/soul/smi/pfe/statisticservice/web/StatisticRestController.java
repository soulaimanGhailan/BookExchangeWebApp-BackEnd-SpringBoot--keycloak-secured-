package soul.smi.pfe.statisticservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soul.smi.pfe.statisticservice.model.TopOwner;
import soul.smi.pfe.statisticservice.model.UserEntity;
import soul.smi.pfe.statisticservice.service.BookRestClient;
import soul.smi.pfe.statisticservice.service.ChatComRestClient;
import soul.smi.pfe.statisticservice.service.UsersRestClient;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticRestController {
    private BookRestClient bookRestClient ;
    private ChatComRestClient chatComRestClient;
    private UsersRestClient usersRestClient ;
    @GetMapping("/topUsers/{number}")
    public List<TopOwner> getTopUsers(@PathVariable int number){
        return bookRestClient.getTopUsers(number);
    }
    @GetMapping("/books")
    public Long getNumberOfBooks(){
        return bookRestClient.getNumberOfBooks();
    }
    @GetMapping("/comments")
    public Long getNumberOfComments(){
        return chatComRestClient.getNumberOfComments();
    }
    @GetMapping("/users")
    public Long getNumberOfUsers(){
        return usersRestClient.getNumberOfUsers();
    }

}
