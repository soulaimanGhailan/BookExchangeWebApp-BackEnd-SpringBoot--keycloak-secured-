package soul.smi.pfe.statisticservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "books-service")
public interface BookRestClient {
    @GetMapping("books/totalNumber")
    public Long getNumberOfBooks();
}
