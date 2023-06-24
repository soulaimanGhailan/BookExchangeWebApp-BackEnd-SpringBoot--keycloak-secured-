package soul.smi.pfe.statisticservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import soul.smi.pfe.statisticservice.model.TopOwner;
import soul.smi.pfe.statisticservice.model.UserEntity;

import java.util.List;

@FeignClient(name = "books-service")
public interface BookRestClient {
    @GetMapping("books/totalNumber")
    public Long getNumberOfBooks();
    @GetMapping("books/topOwners/{number}")
    List<TopOwner> getTopUsers(@PathVariable int number);
}
